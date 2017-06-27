package com.example.demo.domain;


import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {


	@Autowired
	private UserDao userMapper;

	public List<User> searchAll() {
		List<User> list = userMapper.findAll();
		return list;
	}

	public User findById(String id) {
		return userMapper.findById(id);
	}

	public List<User> findByName(String name) {
		return userMapper.findByParam(name);
	}
}
