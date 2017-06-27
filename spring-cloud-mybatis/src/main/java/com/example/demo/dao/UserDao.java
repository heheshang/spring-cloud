package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

	@Select("select * from user")
	@ResultType(User.class)
	List<User> findAll();

	@Select("select * from user where id=#{id}")
	@ResultType(User.class)
	User findById(String id);

	@Select("select * from user where username=#{name}")
	@ResultType(User.class)
	List<User> findByParam(@Param("name") String name);

}
