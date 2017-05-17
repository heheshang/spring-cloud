package com.example.cloud.service;

import com.example.cloud.msg.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.UUID;

@Component
public class SendService {
	@Autowired
	KafkaTemplate kafkaTemplate;
	private Gson gSon = new GsonBuilder().create();

	public void sendMessage() {
		Message m = new Message();
		m.setId(System.currentTimeMillis());
		m.setMsg(UUID.randomUUID().toString());
		m.setSendTime(new Date());
		kafkaTemplate.send("test1", gSon.toJson(m));
	}
}
