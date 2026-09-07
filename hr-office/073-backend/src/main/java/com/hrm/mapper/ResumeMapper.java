package com.hrm.mapper;

import com.hrm.entity.Resume;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ResumeMapper {
    Resume selectById(Long id);
    List<Resume> selectList(@Param("recruitmentId") Long recruitmentId, @Param("name") String name,
                            @Param("status") String status);
    int insert(Resume resume);
    int update(Resume resume);
    int deleteById(Long id);
    int countByRecruitmentId(Long recruitmentId);
}
