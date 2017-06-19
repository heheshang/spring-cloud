package com.example.elasticsearch.service;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Component
public class ElasticsearchDemo {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public List<String> getIkAnalyzeSearchTerms(String searchContent){
		// 调用 IK 分词分词
		AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(
				elasticsearchTemplate.getClient(),
				AnalyzeAction.INSTANCE,
				"index_name",searchContent
		);
		List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
		ikRequest.setTokenizer("ik");

		// 循环赋值
		List<String> searchTermList = new ArrayList<>();
		ikTokenList.forEach(ikToken->{searchTermList.add(ikToken.getTerm());});
		return searchTermList;
	}
}
