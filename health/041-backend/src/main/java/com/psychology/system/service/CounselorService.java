package com.psychology.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psychology.system.entity.Counselor;
import com.psychology.system.entity.TimeSlot;
import com.psychology.system.mapper.CounselorMapper;
import com.psychology.system.mapper.TimeSlotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CounselorService {
    private final CounselorMapper counselorMapper;
    private final TimeSlotMapper timeSlotMapper;

    public List<Counselor> getCounselors() {
        QueryWrapper<Counselor> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "APPROVED");
        return counselorMapper.selectList(wrapper);
    }

    public Counselor getCounselorById(Long id) {
        return counselorMapper.selectById(id);
    }

    public List<TimeSlot> getTimeSlots(Long counselorId) {
        QueryWrapper<TimeSlot> wrapper = new QueryWrapper<>();
        wrapper.eq("counselor_id", counselorId);
        wrapper.eq("status", "AVAILABLE");
        return timeSlotMapper.selectList(wrapper);
    }
}
