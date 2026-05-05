package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.StatisticsReport;
import com.drugreaction.mapper.StatisticsReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsReportService extends BaseCrudService<StatisticsReport> {
    private final StatisticsReportMapper statisticsReportMapper;

    @Override
    protected BaseMapper<StatisticsReport> mapper() {
        return statisticsReportMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stat_no", "dept_name", "stat_month"};
    }
}
