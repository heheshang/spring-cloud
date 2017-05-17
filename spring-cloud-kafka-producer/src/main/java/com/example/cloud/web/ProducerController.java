package com.example.cloud.web;

import com.example.cloud.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
@RestController
public class ProducerController {
	@Autowired
	private SendService sendService;

	@RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
	public void send(@PathVariable("msg") String msg) {
		sendService.sendMessage();
	}
}
