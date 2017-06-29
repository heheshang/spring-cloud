package com.example.cloud.conf;

import com.example.cloud.model.Person;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
@Configuration
public class SqlgridConfiguration {

	@Bean
	IgniteCache<Long, Person> cache() {
		IgniteCache<Long, Person> cache = ignite().cache("Vehicle");

		return cache;
	}

	@Bean
	Ignite ignite() {
		CacheConfiguration cacheCfg = new CacheConfiguration();
		cacheCfg.setName("Vehicle");
		cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
		cacheCfg.setCacheMode(CacheMode.PARTITIONED); // Default.
		cacheCfg.setIndexedTypes(Long.class,Person.class);
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setCacheConfiguration(cacheCfg);
		// Start Ignite node.
		return Ignition.start(cfg);
	}
}
