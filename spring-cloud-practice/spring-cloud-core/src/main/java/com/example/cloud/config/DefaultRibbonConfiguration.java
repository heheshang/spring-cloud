package com.example.cloud.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class DefaultRibbonConfiguration {
	@Value("${ribbon.client.name:#{null}}")
	private String name;

	@Autowired(required = false)
	private IClientConfig config;

	@Autowired
	private PropertiesFactory propertiesFactory;

	@Bean
	public IRule ribbonRule() {
		if ( StringUtils.isEmpty(name)) {
			return null;
		}

		if (this.propertiesFactory.isSet(IRule.class, name)) {
			return this.propertiesFactory.get(IRule.class, config, name);
		}

		// 默认配置
		LabelAndWeightMetadataRule rule = new LabelAndWeightMetadataRule();
		rule.initWithNiwsConfig(config);
		return rule;
	}
}
