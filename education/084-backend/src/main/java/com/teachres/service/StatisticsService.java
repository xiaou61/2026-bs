package com.teachres.service;

import com.teachres.mapper.MaterialDownloadMapper;
import com.teachres.mapper.MaterialInfoMapper;
import com.teachres.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private MaterialDownloadMapper materialDownloadMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long userCount = sysUserMapper.selectList(null, null, 1).size();
        long materialCount = materialInfoMapper.countAll();
        long downloadCount = materialDownloadMapper.countAll();
        long passCount = materialInfoMapper.countByAuditStatus(1);
        BigDecimal passRate = BigDecimal.ZERO;
        if (materialCount > 0) {
            passRate = BigDecimal.valueOf(passCount)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(materialCount), 2, RoundingMode.HALF_UP);
        }
        data.put("userCount", userCount);
        data.put("materialCount", materialCount);
        data.put("downloadCount", downloadCount);
        data.put("passRate", passRate);
        data.put("monthTrend", buildMonthTrend(materialDownloadMapper.selectMonthTrend()));
        data.put("hotTop", buildHotTop(materialDownloadMapper.selectHotTop5()));
        return data;
    }

    private List<Map<String, Object>> buildMonthTrend(List<Map<String, Object>> rows) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Object value = row.get("month_value");
            if (value == null) {
                value = row.get("MONTH_VALUE");
            }
            if (value == null) {
                value = row.get("month");
            }
            if (value == null) {
                value = row.get("MONTH");
            }
            if (value == null) {
                continue;
            }
            String text = String.valueOf(value);
            if (text.length() < 7) {
                continue;
            }
            String month = text.substring(0, 7);
            counts.put(month, counts.getOrDefault(month, 0L) + 1);
        }
        List<Map<String, Object>> trend = new ArrayList<>();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", entry.getKey());
            item.put("value", entry.getValue());
            trend.add(item);
        }
        return trend;
    }

    private List<Map<String, Object>> buildHotTop(List<Map<String, Object>> rows) {
        List<Map<String, Object>> hotTop = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> item = new HashMap<>();
            Object name = row.get("material_name");
            if (name == null) {
                name = row.get("MATERIAL_NAME");
            }
            Object value = row.get("download_count");
            if (value == null) {
                value = row.get("DOWNLOAD_COUNT");
            }
            item.put("name", name);
            item.put("value", value);
            hotTop.add(item);
        }
        return hotTop;
    }
}
