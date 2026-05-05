package com.esgreport.service;

import com.esgreport.mapper.IndicatorLibraryMapper;
import com.esgreport.mapper.DisclosureTemplateMapper;
import com.esgreport.mapper.ReportingPeriodMapper;
import com.esgreport.mapper.CompanySubmissionMapper;
import com.esgreport.mapper.IndicatorDataMapper;
import com.esgreport.mapper.EvidenceFileMapper;
import com.esgreport.mapper.ReviewTaskMapper;
import com.esgreport.mapper.ScoreModelMapper;
import com.esgreport.mapper.EsgScoreMapper;
import com.esgreport.mapper.ImprovementTaskMapper;
import com.esgreport.mapper.StakeholderFeedbackMapper;
import com.esgreport.mapper.ReportExportMapper;
import com.esgreport.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final IndicatorLibraryMapper indicatorLibraryMapper;
    private final DisclosureTemplateMapper disclosureTemplateMapper;
    private final ReportingPeriodMapper reportingPeriodMapper;
    private final CompanySubmissionMapper companySubmissionMapper;
    private final IndicatorDataMapper indicatorDataMapper;
    private final EvidenceFileMapper evidenceFileMapper;
    private final ReviewTaskMapper reviewTaskMapper;
    private final ScoreModelMapper scoreModelMapper;
    private final EsgScoreMapper esgScoreMapper;
    private final ImprovementTaskMapper improvementTaskMapper;
    private final StakeholderFeedbackMapper stakeholderFeedbackMapper;
    private final ReportExportMapper reportExportMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("indicatorCount", indicatorLibraryMapper.countAll());
        data.put("submissionCount", companySubmissionMapper.countAll());
        data.put("reviewCount", reviewTaskMapper.countAll());
        data.put("exportCount", reportExportMapper.countAll());
        data.put("esgTrend", Arrays.asList(18, 27, 36, 45, 58, 66, 78));
        data.put("scorePie", Arrays.asList(map("环境", 35), map("社会", 33), map("治理", 32)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
