package com.datamasking.service;

import com.datamasking.mapper.AccessRequestMapper;
import com.datamasking.mapper.DataSetCatalogMapper;
import com.datamasking.mapper.DataSourceConfigMapper;
import com.datamasking.mapper.MaskingTaskMapper;
import com.datamasking.mapper.RecognitionResultMapper;
import com.datamasking.mapper.RecognitionTaskMapper;
import com.datamasking.mapper.RiskAlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DataSourceConfigMapper sourceMapper;
    private final DataSetCatalogMapper datasetMapper;
    private final RecognitionTaskMapper taskMapper;
    private final RecognitionResultMapper resultMapper;
    private final MaskingTaskMapper maskingMapper;
    private final AccessRequestMapper accessMapper;
    private final RiskAlertMapper alertMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("sourceCount", sourceMapper.selectCount(null));
        data.put("datasetCount", datasetMapper.selectCount(null));
        data.put("taskCount", taskMapper.selectCount(null));
        data.put("resultCount", resultMapper.selectCount(null));
        data.put("maskingCount", maskingMapper.selectCount(null));
        data.put("accessCount", accessMapper.selectCount(null));
        data.put("alertCount", alertMapper.selectCount(null));
        data.put("scanTrend", Arrays.asList(8, 11, 9, 14, 16, 13, 18));
        data.put("riskPie", Arrays.asList(map("身份证", 28), map("手机号", 34), map("邮箱", 18), map("银行卡", 20)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
