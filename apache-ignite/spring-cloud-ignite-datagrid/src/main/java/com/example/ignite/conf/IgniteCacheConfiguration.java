package com.example.ignite.conf;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@Configuration
public class IgniteCacheConfiguration {

	@Bean
	IgniteCache<Integer, String> cache() {
//		CacheConfiguration<Integer, String> cfg = new CacheConfiguration<Integer, String>();
//		cfg.setName("myCache");
//		cfg.setAtomicityMode(TRANSACTIONAL);
		return ignite().getOrCreateCache("myCache");
	}

	@Bean
	Ignite ignite() {
		/*	Ignition.start();
		return Ignition.ignite();*/
		CacheConfiguration cacheCfg = new CacheConfiguration();
		cacheCfg.setName("myCache");
		cacheCfg.setCacheMode(CacheMode.PARTITIONED);
		cacheCfg.setBackups(1);
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setCacheConfiguration(cacheCfg);
		// Start Ignite node.
		return Ignition.start(cfg);

	}
}
