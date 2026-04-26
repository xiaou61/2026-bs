package com.hospital.mapper;

import com.hospital.entity.DoctorReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorReviewMapper {
    List<DoctorReview> selectPage(@Param("doctorId") Long doctorId, @Param("keyword") String keyword);

    List<DoctorReview> selectByDoctorId(@Param("doctorId") Long doctorId);

    DoctorReview selectByAppointmentId(@Param("appointmentId") Long appointmentId);

    void insert(DoctorReview entity);

    void deleteById(@Param("id") Long id);

    long countByUserId(@Param("userId") Long userId);

    long countByDoctorId(@Param("doctorId") Long doctorId);
}
