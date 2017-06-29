package com.example.cloud;

import com.example.cloud.model.Person;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class SpringCloudIgniteSqlgridApplication {

	@Autowired
	IgniteCache<Long, Person> cache;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudIgniteSqlgridApplication.class, args);
	}

	@RequestMapping("/")
	public String index(){
		SqlQuery sqlQuery = new SqlQuery(Person.class, "salary > ?");
		QueryCursor<Map.Entry<Long, Person>> cursor = cache.query(sqlQuery.setArgs(1));
		for ( Map.Entry<Long, Person> entry : cursor ) {
			System.out.println(entry.getValue().toString());
		}
		return "ssss";
	}
}
