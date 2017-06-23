/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*


package com.example.ignite.connect;

import org.apache.ignite.stream.kafka.connect.IgniteSourceTask;

*/
/**
 * Source task mock for tests. It avoids closing the grid from test to test.
 *//*

public class IgniteSourceTaskMock extends IgniteSourceTask {
    */
/** {@inheritDoc} *//*

    @Override public void stop() {
        // Don't stop the grid for tests.
        stopRemoteListen();

        setStopped(true);
    }
}
*/