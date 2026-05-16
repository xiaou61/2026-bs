package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.DutySchedule;
import com.psychologycare.mapper.DutyScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyScheduleService extends BaseCrudService<DutySchedule> {
    private final DutyScheduleMapper dutyScheduleMapper;

    @Override
    protected BaseMapper<DutySchedule> mapper() {
        return dutyScheduleMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"schedule_no", "duty_teacher", "duty_date", "duty_period"};
    }
}







