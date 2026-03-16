package com.teachres.mapper;

import com.teachres.entity.EvalTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvalTaskMapper {
    EvalTask selectById(@Param("id") Long id);

    List<EvalTask> selectList(@Param("taskName") String taskName,
                              @Param("courseId") Long courseId,
                              @Param("status") Integer status,
                              @Param("term") String term);

    List<EvalTask> selectActiveList();

    int insert(EvalTask task);

    int update(EvalTask task);

    int deleteById(@Param("id") Long id);

    long countAll();
}
