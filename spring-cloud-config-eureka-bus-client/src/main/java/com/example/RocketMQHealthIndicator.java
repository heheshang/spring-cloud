package com.example;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class RocketMQHealthIndicator implements HealthIndicator {
	@Override
	public Health health() {
		int errorCode = check();
		if ( errorCode!=0 ){
			return Health.down().withDetail("Error Code",errorCode).build();
		}
		return Health.up().build();
	}

	private int check(){
		//

		return 0;
	}
}
