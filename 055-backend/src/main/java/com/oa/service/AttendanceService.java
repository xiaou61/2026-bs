package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.common.BusinessException;
import com.oa.entity.Attendance;
import com.oa.entity.User;
import com.oa.mapper.AttendanceMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;
    private final UserMapper userMapper;

    private static final LocalTime WORK_START = LocalTime.of(9, 0);
    private static final LocalTime WORK_END = LocalTime.of(18, 0);

    public Attendance getToday(Long userId) {
        return attendanceMapper.selectOne(new LambdaQueryWrapper<Attendance>()
                .eq(Attendance::getUserId, userId)
                .eq(Attendance::getAttendanceDate, LocalDate.now()));
    }

    public void clockIn(Long userId) {
        Attendance today = getToday(userId);
        if (today != null && today.getClockInTime() != null) {
            throw new BusinessException("今日已签到");
        }
        LocalDateTime now = LocalDateTime.now();
        if (today == null) {
            today = new Attendance();
            today.setUserId(userId);
            today.setAttendanceDate(LocalDate.now());
            today.setClockInTime(now);
            today.setStatus(now.toLocalTime().isAfter(WORK_START) ? 1 : 0);
            attendanceMapper.insert(today);
        } else {
            today.setClockInTime(now);
            today.setStatus(now.toLocalTime().isAfter(WORK_START) ? 1 : 0);
            attendanceMapper.updateById(today);
        }
    }

    public void clockOut(Long userId) {
        Attendance today = getToday(userId);
        if (today == null || today.getClockInTime() == null) {
            throw new BusinessException("请先签到");
        }
        if (today.getClockOutTime() != null) {
            throw new BusinessException("今日已签退");
        }
        LocalDateTime now = LocalDateTime.now();
        today.setClockOutTime(now);
        boolean isLate = today.getStatus() == 1;
        boolean isEarly = now.toLocalTime().isBefore(WORK_END);
        if (isLate && isEarly) {
            today.setStatus(4);
        } else if (isEarly) {
            today.setStatus(2);
        }
        attendanceMapper.updateById(today);
    }

    public Page<Attendance> getList(Integer pageNum, Integer pageSize, String month, String keyword) {
        Page<Attendance> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(month)) {
            LocalDate start = LocalDate.parse(month + "-01");
            LocalDate end = start.plusMonths(1).minusDays(1);
            wrapper.between(Attendance::getAttendanceDate, start, end);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.inSql(Attendance::getUserId, "SELECT id FROM user WHERE real_name LIKE '%" + keyword + "%'");
        }
        wrapper.orderByDesc(Attendance::getAttendanceDate);
        Page<Attendance> result = attendanceMapper.selectPage(page, wrapper);
        result.getRecords().forEach(a -> {
            User user = userMapper.selectById(a.getUserId());
            if (user != null) a.setRealName(user.getRealName());
        });
        return result;
    }

    public Page<Attendance> getMyList(Long userId, Integer pageNum, Integer pageSize, String month) {
        Page<Attendance> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attendance::getUserId, userId);
        if (StringUtils.hasText(month)) {
            LocalDate start = LocalDate.parse(month + "-01");
            LocalDate end = start.plusMonths(1).minusDays(1);
            wrapper.between(Attendance::getAttendanceDate, start, end);
        }
        wrapper.orderByDesc(Attendance::getAttendanceDate);
        return attendanceMapper.selectPage(page, wrapper);
    }

    public long getTodayCount() {
        return attendanceMapper.selectCount(new LambdaQueryWrapper<Attendance>()
                .eq(Attendance::getAttendanceDate, LocalDate.now()));
    }
}
