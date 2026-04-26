package com.hospital.mapper;

import com.hospital.entity.DoctorSchedule;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleMapper {
    List<DoctorSchedule> selectPage(@Param("departmentId") Long departmentId,
                                    @Param("doctorId") Long doctorId,
                                    @Param("status") Integer status,
                                    @Param("scheduleDate") LocalDate scheduleDate);

    List<DoctorSchedule> selectMyPage(@Param("doctorId") Long doctorId,
                                      @Param("status") Integer status,
                                      @Param("scheduleDate") LocalDate scheduleDate);

    List<DoctorSchedule> selectPublicList(@Param("departmentId") Long departmentId,
                                          @Param("doctorId") Long doctorId,
                                          @Param("scheduleDate") LocalDate scheduleDate);

    DoctorSchedule selectById(@Param("id") Long id);

    void insert(DoctorSchedule entity);

    void update(DoctorSchedule entity);

    void deleteById(@Param("id") Long id);

    int decreaseSource(@Param("id") Long id);

    int increaseSource(@Param("id") Long id);

    long countByDoctorId(@Param("doctorId") Long doctorId);
}
