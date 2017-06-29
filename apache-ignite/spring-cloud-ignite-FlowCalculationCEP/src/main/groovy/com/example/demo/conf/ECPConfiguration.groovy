package com.example.demo.conf

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Administrator on 2017/6/29 0029.
 */
@Configuration
class ECPConfiguration {

    @Bean
    Ignite ignite(){
        Ignition.start()
    }
}
