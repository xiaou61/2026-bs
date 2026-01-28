package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oa.entity.Schedule;
import com.oa.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleMapper scheduleMapper;

    public List<Schedule> getList(Long userId, String date) {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Schedule::getUserId, userId);
        if (StringUtils.hasText(date)) {
            LocalDate d = LocalDate.parse(date);
            wrapper.apply("DATE(start_time) = {0}", d);
        }
        wrapper.orderByAsc(Schedule::getStartTime);
        return scheduleMapper.selectList(wrapper);
    }

    public void add(Long userId, Schedule schedule) {
        schedule.setUserId(userId);
        scheduleMapper.insert(schedule);
    }

    public void update(Schedule schedule) {
        scheduleMapper.updateById(schedule);
    }

    public void delete(Long id) {
        scheduleMapper.deleteById(id);
    }
}
