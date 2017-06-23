package com.example.ignite

import org.apache.ignite.IgniteAtomicSequence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class ApacheIgniteIdGeneratorApplication {
	@Autowired
	IgniteAtomicSequence sequence

	static void main(String[] args) {
		SpringApplication.run ApacheIgniteIdGeneratorApplication, args
	}
	@RequestMapping("/")
	String getseq(){
		"当前值："+sequence.get()+"加一值"+sequence.getAndIncrement()+"加一以后"+sequence.get()
	}
}
