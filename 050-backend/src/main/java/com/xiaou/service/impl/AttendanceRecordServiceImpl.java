package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.SignDTO;
import com.xiaou.entity.AttendanceRecord;
import com.xiaou.entity.AttendanceTask;
import com.xiaou.mapper.AttendanceRecordMapper;
import com.xiaou.mapper.AttendanceTaskMapper;
import com.xiaou.service.AttendanceRecordService;
import com.xiaou.service.AttendanceStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceRecordServiceImpl extends ServiceImpl<AttendanceRecordMapper, AttendanceRecord> implements AttendanceRecordService {

    @Autowired
    private AttendanceTaskMapper attendanceTaskMapper;

    @Autowired
    private AttendanceStatService attendanceStatService;

    @Override
    @Transactional
    public AttendanceRecord sign(Long studentId, SignDTO dto) {
        AttendanceTask task = attendanceTaskMapper.selectById(dto.getTaskId());
        if (task == null || task.getStatus() != 1) {
            throw new RuntimeException("签到任务不存在或已结束");
        }
        
        if (LocalDateTime.now().isAfter(task.getEndTime())) {
            throw new RuntimeException("签到已结束");
        }
        
        // 获取学生的签到记录
        AttendanceRecord record = this.getOne(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, dto.getTaskId())
                .eq(AttendanceRecord::getStudentId, studentId));
        
        if (record == null) {
            throw new RuntimeException("您不在此课程的签到名单中");
        }
        
        if (record.getStatus() != 0) {
            throw new RuntimeException("您已完成签到，请勿重复签到");
        }
        
        // 根据签到类型验证
        switch (task.getSignType()) {
            case 2: // 定位签到
                if (!validateLocation(task, dto.getLatitude(), dto.getLongitude())) {
                    throw new RuntimeException("您不在签到范围内");
                }
                record.setLatitude(dto.getLatitude());
                record.setLongitude(dto.getLongitude());
                record.setAddress(dto.getAddress());
                break;
            case 3: // 二维码签到
                if (!task.getQrCode().equals(dto.getSignCode())) {
                    throw new RuntimeException("二维码无效或已过期");
                }
                break;
            case 4: // 数字签到
                if (!task.getSignCode().equals(dto.getSignCode())) {
                    throw new RuntimeException("签到码错误");
                }
                break;
        }
        
        // 判断是否迟到
        if (task.getLateTime() != null && LocalDateTime.now().isAfter(task.getLateTime())) {
            record.setStatus(2); // 迟到
        } else {
            record.setStatus(1); // 正常签到
        }
        
        record.setSignTime(LocalDateTime.now());
        record.setDeviceInfo(dto.getDeviceInfo());
        this.updateById(record);
        
        // 更新考勤统计
        attendanceStatService.refreshStat(studentId, task.getCourseId());
        
        return record;
    }

    private boolean validateLocation(AttendanceTask task, BigDecimal lat, BigDecimal lng) {
        if (task.getLatitude() == null || task.getLongitude() == null) {
            return true;
        }
        if (lat == null || lng == null) {
            return false;
        }
        
        // 计算距离（简化计算，实际应使用更精确的球面距离公式）
        double earthRadius = 6371000; // 地球半径（米）
        double dLat = Math.toRadians(lat.doubleValue() - task.getLatitude().doubleValue());
        double dLng = Math.toRadians(lng.doubleValue() - task.getLongitude().doubleValue());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(task.getLatitude().doubleValue())) *
                        Math.cos(Math.toRadians(lat.doubleValue())) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        
        return distance <= task.getDistance();
    }

    @Override
    public IPage<AttendanceRecord> pageRecordsByTask(Integer page, Integer size, Long taskId) {
        return this.page(new Page<>(page, size),
                new LambdaQueryWrapper<AttendanceRecord>()
                        .eq(AttendanceRecord::getTaskId, taskId)
                        .orderByAsc(AttendanceRecord::getStatus)
                        .orderByDesc(AttendanceRecord::getSignTime));
    }

    @Override
    public List<AttendanceRecord> getStudentRecords(Long studentId, Long courseId) {
        // 先获取课程的所有签到任务
        List<AttendanceTask> tasks = attendanceTaskMapper.selectList(
                new LambdaQueryWrapper<AttendanceTask>()
                        .eq(AttendanceTask::getCourseId, courseId));
        if (tasks.isEmpty()) {
            return List.of();
        }
        
        List<Long> taskIds = tasks.stream().map(AttendanceTask::getId).toList();
        return this.list(new LambdaQueryWrapper<AttendanceRecord>()
                .in(AttendanceRecord::getTaskId, taskIds)
                .eq(AttendanceRecord::getStudentId, studentId)
                .orderByDesc(AttendanceRecord::getCreateTime));
    }

    @Override
    public Map<String, Object> getTaskSignStats(Long taskId) {
        Map<String, Object> stats = new HashMap<>();
        
        Long total = this.count(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, taskId));
        Long unsigned = this.count(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, taskId)
                .eq(AttendanceRecord::getStatus, 0));
        Long normal = this.count(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, taskId)
                .eq(AttendanceRecord::getStatus, 1));
        Long late = this.count(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, taskId)
                .eq(AttendanceRecord::getStatus, 2));
        Long leave = this.count(new LambdaQueryWrapper<AttendanceRecord>()
                .eq(AttendanceRecord::getTaskId, taskId)
                .eq(AttendanceRecord::getStatus, 4));
        
        stats.put("total", total);
        stats.put("unsigned", unsigned);
        stats.put("normal", normal);
        stats.put("late", late);
        stats.put("leave", leave);
        stats.put("signRate", total > 0 ? (normal + late) * 100.0 / total : 0);
        
        return stats;
    }
}
