package com.hrm.mapper;

import com.hrm.entity.Recruitment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface RecruitmentMapper {
    Recruitment selectById(Long id);
    List<Recruitment> selectList(@Param("positionId") Long positionId, @Param("departmentId") Long departmentId,
                                 @Param("status") String status);
    List<Recruitment> selectOpen();
    int insert(Recruitment recruitment);
    int update(Recruitment recruitment);
    int deleteById(Long id);
    int countByStatus(String status);
}
