package com.example.ignite.conf;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
@Configuration
public class IgniteConfig {

	@Bean
	public Ignite ignite(){
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setPeerClassLoadingEnabled(true);
		cfg.setDiscoverySpi(tcpDiscoverySpi());
		return Ignition.start(cfg);
	}


	@Bean
	TcpDiscoverySpi tcpDiscoverySpi(){
		TcpDiscoverySpi spi = new TcpDiscoverySpi();
		TcpDiscoveryVmIpFinder ipFinder = new  TcpDiscoveryVmIpFinder().setAddresses(Arrays.asList(new String[]{"192.168.61.129:47500..47503"}));
		spi.setIpFinder(ipFinder);
		return spi;
	}
}
