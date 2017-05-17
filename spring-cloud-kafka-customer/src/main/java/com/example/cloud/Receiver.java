package com.example.cloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
@Component
public class Receiver {

	private Gson gson = new GsonBuilder().create();

	@KafkaListener(topics = "test1")
	public void processMessage(String content) {
		System.out.println(content);
		Message m = gson.fromJson(content, Message.class);
		System.out.println(m.toString());
	}
}