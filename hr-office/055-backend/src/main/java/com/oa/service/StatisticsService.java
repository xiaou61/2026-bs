package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oa.entity.Attendance;
import com.oa.entity.Department;
import com.oa.entity.LeaveRequest;
import com.oa.entity.User;
import com.oa.mapper.AttendanceMapper;
import com.oa.mapper.DepartmentMapper;
import com.oa.mapper.LeaveRequestMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final AttendanceMapper attendanceMapper;
    private final LeaveRequestMapper leaveRequestMapper;

    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getStatus, 1)));
        result.put("deptCount", departmentMapper.selectCount(null));
        result.put("todayAttendance", attendanceMapper.selectCount(new LambdaQueryWrapper<Attendance>().eq(Attendance::getAttendanceDate, LocalDate.now())));
        result.put("pendingLeave", leaveRequestMapper.selectCount(new LambdaQueryWrapper<LeaveRequest>().eq(LeaveRequest::getStatus, 0)));
        return result;
    }
}
