package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.Attendance;
import com.hrm.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceService {
    @Resource
    private AttendanceMapper attendanceMapper;

    public Attendance getById(Long id) {
        return attendanceMapper.selectById(id);
    }

    public PageInfo<Attendance> getList(Long employeeId, String employeeName, Date startDate, Date endDate,
                                         String status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(attendanceMapper.selectList(employeeId, employeeName, startDate, endDate, status));
    }

    public void clockIn(Long employeeId) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(today));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Attendance existing = attendanceMapper.selectByEmployeeAndDate(employeeId, today);
        if (existing != null) {
            throw new BusinessException("今日已打卡");
        }
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(today);
        attendance.setClockIn(new Date());
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");
        String time = timeFmt.format(new Date());
        if (time.compareTo("09:00") > 0) {
            attendance.setStatus("late");
            attendance.setRemark("迟到");
        } else {
            attendance.setStatus("normal");
        }
        attendanceMapper.insert(attendance);
    }

    public void clockOut(Long employeeId) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(today));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Attendance existing = attendanceMapper.selectByEmployeeAndDate(employeeId, today);
        if (existing == null) {
            throw new BusinessException("今日尚未打上班卡");
        }
        if (existing.getClockOut() != null) {
            throw new BusinessException("今日已打下班卡");
        }
        existing.setClockOut(new Date());
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");
        String time = timeFmt.format(new Date());
        if (time.compareTo("18:00") < 0 && !"late".equals(existing.getStatus())) {
            existing.setStatus("early");
            existing.setRemark("早退");
        }
        attendanceMapper.update(existing);
    }

    public Attendance getTodayAttendance(Long employeeId) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(today));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendanceMapper.selectByEmployeeAndDate(employeeId, today);
    }

    public void add(Attendance attendance) {
        attendanceMapper.insert(attendance);
    }

    public void update(Attendance attendance) {
        attendanceMapper.update(attendance);
    }

    public void delete(Long id) {
        attendanceMapper.deleteById(id);
    }

    public Map<String, Object> getTodayStatistics() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(today));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", attendanceMapper.countByDate(today));
        stats.put("normal", attendanceMapper.countByStatus("normal", today));
        stats.put("late", attendanceMapper.countByStatus("late", today));
        stats.put("early", attendanceMapper.countByStatus("early", today));
        return stats;
    }
}
