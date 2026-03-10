package com.teachres.mapper;

import com.teachres.entity.StudyList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyListMapper {
    StudyList selectByUserAndMaterial(@Param("userId") Long userId, @Param("materialId") Long materialId);
    List<StudyList> selectByUserId(Long userId);
    int insert(StudyList studyList);
    int update(StudyList studyList);
    int deleteById(Long id);
}
