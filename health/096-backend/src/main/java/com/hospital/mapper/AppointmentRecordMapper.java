package com.hospital.mapper;

import com.hospital.entity.AppointmentRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AppointmentRecordMapper {
    List<AppointmentRecord> selectPage(@Param("keyword") String keyword,
                                       @Param("status") Integer status,
                                       @Param("doctorId") Long doctorId,
                                       @Param("departmentId") Long departmentId);

    List<AppointmentRecord> selectMyPage(@Param("userId") Long userId, @Param("status") Integer status);

    List<AppointmentRecord> selectDoctorPage(@Param("doctorId") Long doctorId, @Param("status") Integer status);

    AppointmentRecord selectById(@Param("id") Long id);

    void insert(AppointmentRecord entity);

    void bindOrderId(@Param("id") Long id, @Param("orderId") Long orderId);

    void updateStatus(@Param("id") Long id,
                      @Param("status") Integer status,
                      @Param("payTime") java.time.LocalDateTime payTime,
                      @Param("finishTime") java.time.LocalDateTime finishTime);

    long countAll();

    long countByUserId(@Param("userId") Long userId);

    long countPaidByUserId(@Param("userId") Long userId);

    long countByDoctorId(@Param("doctorId") Long doctorId);

    long countFinishedByDoctorId(@Param("doctorId") Long doctorId);

    List<Map<String, Object>> selectDepartmentRank();

    List<Map<String, Object>> selectAppointmentTrend();
}
