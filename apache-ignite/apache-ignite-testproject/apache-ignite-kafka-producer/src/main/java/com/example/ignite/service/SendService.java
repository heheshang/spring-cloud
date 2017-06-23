package com.example.ignite.service;

import com.example.ignite.msg.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SendService {
	@Autowired
	KafkaTemplate kafkaTemplate;
	private Gson gSon = new GsonBuilder().create();

	public void sendMessage(String str) {
		Message m = new Message();
		m.setId(System.currentTimeMillis());
		m.setMsg(str);
		m.setSendTime(new Date());
		kafkaTemplate.send("test1", gSon.toJson(m));
	}
}
