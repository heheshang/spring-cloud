package com.example.ignite;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
//@Configuration
public class DataNodeIgniteConfiguration {
//
//	@Bean
//	public IgniteConfiguration igniteConfiguration() {
//		IgniteConfiguration cfg = new IgniteConfiguration();
//		//Enabling the peer-class loading feature.
//		cfg.setPeerClassLoadingEnabled(true);
//
//		Map<String, Boolean> map = new HashMap<>();
//		map.put("data.node", true);
//		cfg.setUserAttributes(map);
//
//		cfg.setCacheConfiguration(getVehiclesrecords(),getMaintenanceRecords());
//		return cfg;
//
//	}
//	@Bean(name = "discoverySpi")
//	TcpDiscoverySpi discoverySpi(){
//		TcpDiscoverySpi spi = new TcpDiscoverySpi();
//		spi.setIpFinder(tcpDiscoveryVmIpFinder());
//		return spi;
//	}
//
//	@Bean
//	TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder(){
//		TcpDiscoveryVmIpFinder finder = new TcpDiscoveryVmIpFinder();
//		List<String> strings = new ArrayList<>();
//		for ( int i = 47500; i < 47509; i++ ) {
//			 strings.add("127.0.0.1:"+i);
//
//		}
//		finder.setAddresses(strings);
//		return finder;
//	}
//
//	//<!-- Cache for Vehicles' records --
//	@Bean
//	CacheConfiguration getVehiclesrecords(){
//		CacheConfiguration configuration = new CacheConfiguration();
//		configuration.setName("vehicles");
//		configuration.setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED);
//		configuration.setNodeFilter(dataNodeFilter());
//		return configuration;
//	}
//
//	//	Cache for Maintenance records
//	@Bean
//	CacheConfiguration getMaintenanceRecords(){
//		CacheConfiguration configuration = new CacheConfiguration();
//		configuration.setName("vehicles");
//		configuration.setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED);
//		configuration.setNodeFilter(dataNodeFilter());
//		configuration.setCacheStoreFactory(factoryBuilder());
//		configuration.setStoreKeepBinary(true);
//		configuration.setWriteThrough(true);
//		configuration.setReadThrough(true);
//		List<QueryEntity> list = new ArrayList<>();
//		list.add(queryEntity());
//		configuration.setQueryEntities(list);
//		return configuration;
//	}
//
//
//	@Bean
//	DataNodeFilter dataNodeFilter(){
//		return  new DataNodeFilter();
//	}
//
//	@Bean
//	Factory<Object> factoryBuilder(){
//		return FactoryBuilder.factoryOf("com.example.ignite.common.filters.cachestore.SimpleCacheStore");
//
//	}
//	@Bean
//	QueryEntity queryEntity(){
//		QueryEntity queryEntity = new QueryEntity();
//		queryEntity.setKeyType("java.lang.Integer");
//		queryEntity.setValueType("com.example.ignite.services.maintenance.common.Maintenance");
//
//		LinkedHashMap<String,String> map = new LinkedHashMap<>();
//		map.put("vehicleId","java.lang.Integer");
//		queryEntity.setFields(map);
//		List<QueryIndex> list = new ArrayList<>();
//		list.add(queryIndex());
//		queryEntity.setIndexes(list);
//
//		return queryEntity;
//	}
//	@Bean
//	QueryIndex queryIndex(){
//		return new QueryIndex("vehicleId");
//	}
}
