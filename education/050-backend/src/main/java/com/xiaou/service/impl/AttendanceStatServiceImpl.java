package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.AttendanceRecord;
import com.xiaou.entity.AttendanceStat;
import com.xiaou.entity.AttendanceTask;
import com.xiaou.mapper.AttendanceRecordMapper;
import com.xiaou.mapper.AttendanceStatMapper;
import com.xiaou.mapper.AttendanceTaskMapper;
import com.xiaou.service.AttendanceStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceStatServiceImpl extends ServiceImpl<AttendanceStatMapper, AttendanceStat> implements AttendanceStatService {

    @Autowired
    private AttendanceTaskMapper attendanceTaskMapper;

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public AttendanceStat getStudentCourseStat(Long studentId, Long courseId) {
        return this.getOne(new LambdaQueryWrapper<AttendanceStat>()
                .eq(AttendanceStat::getStudentId, studentId)
                .eq(AttendanceStat::getCourseId, courseId));
    }

    @Override
    public List<AttendanceStat> getStudentAllStats(Long studentId) {
        return this.list(new LambdaQueryWrapper<AttendanceStat>()
                .eq(AttendanceStat::getStudentId, studentId));
    }

    @Override
    public List<AttendanceStat> getCourseAllStats(Long courseId) {
        return this.list(new LambdaQueryWrapper<AttendanceStat>()
                .eq(AttendanceStat::getCourseId, courseId)
                .orderByDesc(AttendanceStat::getAttendanceRate));
    }

    @Override
    public void refreshStat(Long studentId, Long courseId) {
        // 获取课程的所有签到任务
        List<AttendanceTask> tasks = attendanceTaskMapper.selectList(
                new LambdaQueryWrapper<AttendanceTask>()
                        .eq(AttendanceTask::getCourseId, courseId));
        
        if (tasks.isEmpty()) {
            return;
        }
        
        List<Long> taskIds = tasks.stream().map(AttendanceTask::getId).toList();
        
        // 获取学生在这些任务中的签到记录
        List<AttendanceRecord> records = attendanceRecordMapper.selectList(
                new LambdaQueryWrapper<AttendanceRecord>()
                        .in(AttendanceRecord::getTaskId, taskIds)
                        .eq(AttendanceRecord::getStudentId, studentId));
        
        // 统计各种状态的数量
        int totalCount = records.size();
        int normalCount = 0;
        int lateCount = 0;
        int absentCount = 0;
        int leaveCount = 0;
        int makeupCount = 0;
        
        for (AttendanceRecord record : records) {
            switch (record.getStatus()) {
                case 0 -> absentCount++;
                case 1 -> normalCount++;
                case 2 -> lateCount++;
                case 4 -> leaveCount++;
                case 5 -> makeupCount++;
            }
        }
        
        // 计算出勤率（正常+迟到+补签）/ 总数
        BigDecimal attendanceRate = BigDecimal.ZERO;
        if (totalCount > 0) {
            int attended = normalCount + lateCount + makeupCount;
            attendanceRate = new BigDecimal(attended * 100)
                    .divide(new BigDecimal(totalCount), 2, RoundingMode.HALF_UP);
        }
        
        // 查找或创建统计记录
        AttendanceStat stat = this.getOne(new LambdaQueryWrapper<AttendanceStat>()
                .eq(AttendanceStat::getStudentId, studentId)
                .eq(AttendanceStat::getCourseId, courseId));
        
        if (stat == null) {
            stat = new AttendanceStat();
            stat.setStudentId(studentId);
            stat.setCourseId(courseId);
        }
        
        stat.setTotalCount(totalCount);
        stat.setNormalCount(normalCount);
        stat.setLateCount(lateCount);
        stat.setAbsentCount(absentCount);
        stat.setLeaveCount(leaveCount);
        stat.setMakeupCount(makeupCount);
        stat.setAttendanceRate(attendanceRate);
        stat.setUpdateTime(LocalDateTime.now());
        
        this.saveOrUpdate(stat);
    }

    @Override
    public Map<String, Object> getCourseStatSummary(Long courseId) {
        Map<String, Object> summary = new HashMap<>();
        
        List<AttendanceStat> stats = this.getCourseAllStats(courseId);
        
        if (stats.isEmpty()) {
            summary.put("studentCount", 0);
            summary.put("avgAttendanceRate", 0);
            return summary;
        }
        
        summary.put("studentCount", stats.size());
        
        // 计算平均出勤率
        BigDecimal totalRate = stats.stream()
                .map(AttendanceStat::getAttendanceRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgRate = totalRate.divide(new BigDecimal(stats.size()), 2, RoundingMode.HALF_UP);
        summary.put("avgAttendanceRate", avgRate);
        
        // 统计各出勤率区间的学生数
        long excellent = stats.stream().filter(s -> s.getAttendanceRate().compareTo(new BigDecimal("90")) >= 0).count();
        long good = stats.stream().filter(s -> s.getAttendanceRate().compareTo(new BigDecimal("80")) >= 0 
                && s.getAttendanceRate().compareTo(new BigDecimal("90")) < 0).count();
        long pass = stats.stream().filter(s -> s.getAttendanceRate().compareTo(new BigDecimal("60")) >= 0 
                && s.getAttendanceRate().compareTo(new BigDecimal("80")) < 0).count();
        long fail = stats.stream().filter(s -> s.getAttendanceRate().compareTo(new BigDecimal("60")) < 0).count();
        
        summary.put("excellent", excellent); // 90%以上
        summary.put("good", good); // 80-90%
        summary.put("pass", pass); // 60-80%
        summary.put("fail", fail); // 60%以下
        
        return summary;
    }
}
