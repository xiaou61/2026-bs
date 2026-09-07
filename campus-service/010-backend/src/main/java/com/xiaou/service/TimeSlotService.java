package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.TimeSlot;
import com.xiaou.mapper.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlot> listAll() {
        LambdaQueryWrapper<TimeSlot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimeSlot::getStatus, 1);
        wrapper.orderByAsc(TimeSlot::getSortOrder);
        return timeSlotMapper.selectList(wrapper);
    }

    public TimeSlot getByCode(String code) {
        LambdaQueryWrapper<TimeSlot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimeSlot::getSlotCode, code);
        return timeSlotMapper.selectOne(wrapper);
    }

    public TimeSlot save(TimeSlot timeSlot) {
        if (timeSlot.getId() == null) {
            timeSlot.setCreateTime(LocalDateTime.now());
        }
        timeSlot.setUpdateTime(LocalDateTime.now());
        if (timeSlot.getId() == null) {
            timeSlotMapper.insert(timeSlot);
        } else {
            timeSlotMapper.updateById(timeSlot);
        }
        return timeSlot;
    }

    public void delete(Long id) {
        timeSlotMapper.deleteById(id);
    }
}

