package com.hrm.mapper;

import com.hrm.entity.Training;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface TrainingMapper {
    Training selectById(Long id);
    List<Training> selectList(@Param("title") String title, @Param("status") Integer status);
    int insert(Training training);
    int update(Training training);
    int deleteById(Long id);
}
