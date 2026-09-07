package com.programming.learning.mapper;

import com.programming.learning.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 章节Mapper接口
 */
@Mapper
public interface ChapterMapper {

    int insert(Chapter chapter);

    int deleteById(Long id);

    int update(Chapter chapter);

    Chapter selectById(Long id);

    List<Chapter> selectByCourseId(Long courseId);

    Long countByCourseId(Long courseId);

    int incrementViewCount(Long id);

    int deleteByCourseId(Long courseId);
}
