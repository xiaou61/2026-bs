package com.teachres.mapper;

import com.teachres.entity.CourseCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCategoryMapper {
    CourseCategory selectById(@Param("id") Long id);

    List<CourseCategory> selectList(@Param("name") String name,
                                    @Param("status") Integer status);

    List<CourseCategory> selectEnabledList();

    int countByCode(@Param("code") String code);

    int insert(CourseCategory category);

    int update(CourseCategory category);

    int deleteById(@Param("id") Long id);
}
