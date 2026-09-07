package com.kindergarten.mapper;

import com.kindergarten.entity.SchoolTerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolTermMapper {
    List<SchoolTerm> selectList(@Param("termName") String termName, @Param("status") Integer status, @Param("currentFlag") Integer currentFlag);
    List<SchoolTerm> selectEnabled();
    SchoolTerm selectById(@Param("id") Long id);
    int insert(SchoolTerm entity);
    int update(SchoolTerm entity);
    int deleteById(@Param("id") Long id);
}
