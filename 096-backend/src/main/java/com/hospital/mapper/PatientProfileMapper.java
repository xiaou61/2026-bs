package com.hospital.mapper;

import com.hospital.entity.PatientProfile;
import org.apache.ibatis.annotations.Param;

public interface PatientProfileMapper {
    PatientProfile selectByUserId(@Param("userId") Long userId);

    void insert(PatientProfile entity);

    void update(PatientProfile entity);
}
