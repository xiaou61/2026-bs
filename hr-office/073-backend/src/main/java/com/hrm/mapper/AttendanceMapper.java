package com.hrm.mapper;

import com.hrm.entity.Attendance;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

public interface AttendanceMapper {
    Attendance selectById(Long id);
    Attendance selectByEmployeeAndDate(@Param("employeeId") Long employeeId, @Param("date") Date date);
    List<Attendance> selectList(@Param("employeeId") Long employeeId, @Param("employeeName") String employeeName,
                                @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                @Param("status") String status);
    int insert(Attendance attendance);
    int update(Attendance attendance);
    int deleteById(Long id);
    int countByStatus(@Param("status") String status, @Param("date") Date date);
    int countByDate(Date date);
}
