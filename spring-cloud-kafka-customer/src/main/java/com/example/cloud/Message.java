package com.example.cloud;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class Message implements Serializable{
	private Long id;
	private String msg;
	private Date sendTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
