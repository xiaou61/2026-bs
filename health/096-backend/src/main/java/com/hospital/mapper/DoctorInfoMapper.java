package com.hospital.mapper;

import com.hospital.entity.DoctorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DoctorInfoMapper {
    List<DoctorInfo> selectPage(@Param("departmentId") Long departmentId, @Param("keyword") String keyword, @Param("status") Integer status);

    List<DoctorInfo> selectPublicList(@Param("departmentId") Long departmentId, @Param("keyword") String keyword);

    List<DoctorInfo> selectEnabled();

    DoctorInfo selectById(@Param("id") Long id);

    DoctorInfo selectByUserId(@Param("userId") Long userId);

    void insert(DoctorInfo entity);

    void update(DoctorInfo entity);

    void deleteById(@Param("id") Long id);

    long countAll();

    List<Map<String, Object>> selectHotDoctorRank();
}
