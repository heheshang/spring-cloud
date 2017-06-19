package com.example.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
@Document(indexName = "province",type = "city")
public class City {

	private static final long serialVersionUID = -1L;

	/**
	 * 城市编号
	 */
	private Long id;
	private Long provinceid;

	/**
	 * 城市名称
	 */
	private String cityname;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 城市评分
	 */
	private Integer score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Long provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("City{");
		sb.append("id=").append(id);
		sb.append(", provinceid=").append(provinceid);
		sb.append(", cityname='").append(cityname).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", score=").append(score);
		sb.append('}');
		return sb.toString();
	}
}
