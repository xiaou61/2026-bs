package com.course.mapper;

import com.course.entity.AcademicTerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcademicTermMapper {
    List<AcademicTerm> selectList(@Param("termName") String termName, @Param("status") Integer status, @Param("currentFlag") Integer currentFlag);
    List<AcademicTerm> selectEnabled();
    AcademicTerm selectById(@Param("id") Long id);
    int insert(AcademicTerm entity);
    int update(AcademicTerm entity);
    int deleteById(@Param("id") Long id);
}
