package com.example.ignite.conf

import org.apache.ignite.Ignite
import org.apache.ignite.IgniteAtomicSequence
import org.apache.ignite.Ignition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Administrator on 2017/6/21 0021.
 */
@Configuration
class IdGeneratorConfiguration {


    @Bean
    Ignite ignite() {
        Ignition.start()

    }

    @Bean
    IgniteAtomicSequence sequence() {
         ignite().atomicSequence("seqName", 1, true)
    }

}
