package com.example.cloud.dao.cluster;

import com.example.cloud.domain.City;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 城市 DAO 接口类
 *
 * Created by bysocket on 07/02/2017.
 */
//@Mapper
public interface CityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    @Select("select id, province_id, city_name, description from City where city_name=#{cityName}")
    @Results({
            @Result(property = "id",column = "id",javaType = Long.class),
            @Result(property = "provinceId",column = "province_id",javaType = Long.class),
            @Result(property = "cityName",column = "city_name"),
            @Result(property = "description",column = "description")
    })
    City findByName(@Param("cityName") String cityName);
}
