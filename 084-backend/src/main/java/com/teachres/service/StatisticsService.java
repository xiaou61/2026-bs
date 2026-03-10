package com.teachres.service;

import com.teachres.mapper.MaterialDownloadMapper;
import com.teachres.mapper.MaterialInfoMapper;
import com.teachres.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        data.put("monthTrend", materialDownloadMapper.selectMonthTrend());
        data.put("hotTop", materialDownloadMapper.selectHotTop5());
        return data;
    }
}
