/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package services.maintenance;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.cache.Cache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import services.maintenance.common.Maintenance;
import services.maintenance.common.MaintenanceService;
import services.vehicles.common.VehicleService;

/**
 * An implementation of {@link MaintenanceService} that will be deployed in the cluster.
 * </p>
 * The implementation stores maintenance records in a dedicated distributed cache deployed on Data Nodes.
 */
public class MaintenanceServiceImpl implements MaintenanceService {
    @IgniteInstanceResource
    private Ignite ignite;

    /** Reference to the cache. */
    private IgniteCache<Integer, Maintenance> maintCache;

    /** Maintenance IDs generator */
    private IgniteAtomicSequence sequence;

    /** Processor that accepts requests from external apps that don't use Apache Ignite API. */
    private ExternalCallsProcessor externalCallsProcessor;

    /** {@inheritDoc} */
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Initializing Maintenance Service on node:" + ignite.cluster().localNode());

        /**
         * It's assumed that the cache has already been deployed. To do that, make sure to start Data Nodes with
         * a respective cache configuration.
         */
        maintCache = ignite.cache("maintenance");

        /** Processor that accepts requests from external apps that don't use Apache Ignite API. */
        externalCallsProcessor = new ExternalCallsProcessor();

        externalCallsProcessor.start();
    }

    /** {@inheritDoc} */
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing Maintenance Service on node:" + ignite.cluster().localNode());

        /**
         * Getting the sequence that will be used for IDs generation.
         */
        sequence = ignite.atomicSequence("MaintenanceIds", 1, true);
    }

    /** {@inheritDoc} */
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping Maintenance Service on node:" + ignite.cluster().localNode());

        // Stopping external requests processor.
        externalCallsProcessor.interrupt();
    }

    /** {@inheritDoc} */
    public Date scheduleVehicleMaintenance(int vehicleId) {
        Date date = new Date();

        // Getting access to VehicleService proxy. The proxy allows to call remotely deployed services.
        VehicleService vehicleService = ignite.services().serviceProxy(VehicleService.SERVICE_NAME,
            VehicleService.class, false);

        // Calling remote service to double check vehicle's existence.
        if (vehicleService.getVehicle(vehicleId) == null)
            throw new RuntimeException("Vehicle with provided ID doesn't exist:" + vehicleId);

        // Remembering scheduled appointment.
        maintCache.put((int)sequence.getAndIncrement(), new Maintenance(vehicleId, date));

        return date;
    }

    /** {@inheritDoc} */
    public List<Maintenance> getMaintenanceRecords(int vehicleId) {
        SqlQuery<Long, Maintenance> query = new SqlQuery<Long, Maintenance>(Maintenance.class,
            "WHERE vehicleId = ?").setArgs(vehicleId);

        List<Cache.Entry<Long, Maintenance>> res = maintCache.query(query).getAll();

        List<Maintenance> res2 = new ArrayList<Maintenance>(res.size());

        for (Cache.Entry<Long, Maintenance> entry : res)
            res2.add(entry.getValue());

        return res2;
    }

    /**
     * Thread that accepts request from external applications that don't use Apache Ignite service grid API.
     */
    private class ExternalCallsProcessor extends Thread {
        /** Server socket to accept external connections. */
        private ServerSocket externalConnect;

        /** {@inheritDoc} */
        @Override public void run() {
            try {
                externalConnect = new ServerSocket(50000);

                while (!isInterrupted()) {
                    Socket socket = externalConnect.accept();

                    DataInputStream dis = new DataInputStream(socket.getInputStream());

                    // Getting vehicleId.
                    int vehicleId = dis.readInt();

                    List<Maintenance> result = getMaintenanceRecords(vehicleId);

                    ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());

                    // Writing the result into the socket.
                    dos.writeObject(result);

                    dis.close();
                    dos.close();
                    socket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        /** {@inheritDoc} */
        @Override public void interrupt() {
            super.interrupt();

            try {
                externalConnect.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
