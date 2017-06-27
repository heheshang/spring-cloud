package com.example.demo.web;

import com.example.demo.domain.UserService;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> readUserInfo() {
		List<User> ls = userService.searchAll();
		return ls;
	}


	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public User findById(@PathVariable String id) {
		return userService.findById(id);
	}


	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public List<User> findByName(@PathVariable String name) {
		return userService.findByName(name);
	}
}
