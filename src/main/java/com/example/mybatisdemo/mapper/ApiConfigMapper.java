package com.example.mybatisdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ApiConfigMapper {
    List<Map<String, Object>> selectApiConfig(@Param("apiId") String apiId);
}
