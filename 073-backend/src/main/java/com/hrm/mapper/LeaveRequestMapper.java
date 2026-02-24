package com.hrm.mapper;

import com.hrm.entity.LeaveRequest;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LeaveRequestMapper {
    LeaveRequest selectById(Long id);
    List<LeaveRequest> selectList(@Param("employeeId") Long employeeId, @Param("employeeName") String employeeName,
                                  @Param("type") String type, @Param("status") String status);
    int insert(LeaveRequest leaveRequest);
    int update(LeaveRequest leaveRequest);
    int deleteById(Long id);
    int countByStatus(String status);
}
