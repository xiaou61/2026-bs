package com.opera.mapper;

import com.opera.entity.CulturalSeason;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CulturalSeasonMapper {
    List<CulturalSeason> selectList(@Param("termName") String termName, @Param("status") Integer status, @Param("currentFlag") Integer currentFlag);
    List<CulturalSeason> selectEnabled();
    CulturalSeason selectById(@Param("id") Long id);
    int insert(CulturalSeason entity);
    int update(CulturalSeason entity);
    int deleteById(@Param("id") Long id);
}


