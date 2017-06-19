package com.example.elasticsearch.web;

import com.example.elasticsearch.service.ElasticsearchDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@RestController
public class IndexController {
	@Autowired
	ElasticsearchDemo elasticsearchDemo;
	@RequestMapping("/index")
	public List<String>  index(String searchContent){
		return elasticsearchDemo.getIkAnalyzeSearchTerms(searchContent);
	}
}
