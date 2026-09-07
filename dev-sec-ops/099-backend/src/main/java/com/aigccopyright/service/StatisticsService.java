package com.aigccopyright.service;

import com.aigccopyright.entity.AppealRecord;
import com.aigccopyright.entity.AuditResult;
import com.aigccopyright.entity.CopyrightRegister;
import com.aigccopyright.entity.EvidenceRecord;
import com.aigccopyright.entity.ImageAsset;
import com.aigccopyright.entity.InfringementClue;
import com.aigccopyright.mapper.AppealRecordMapper;
import com.aigccopyright.mapper.AuditResultMapper;
import com.aigccopyright.mapper.AuditTaskMapper;
import com.aigccopyright.mapper.CopyrightRegisterMapper;
import com.aigccopyright.mapper.EvidenceRecordMapper;
import com.aigccopyright.mapper.ImageAssetMapper;
import com.aigccopyright.mapper.InfringementClueMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ImageAssetMapper imageAssetMapper;

    @Autowired
    private AuditTaskMapper auditTaskMapper;

    @Autowired
    private AuditResultMapper auditResultMapper;

    @Autowired
    private CopyrightRegisterMapper copyrightRegisterMapper;

    @Autowired
    private EvidenceRecordMapper evidenceRecordMapper;

    @Autowired
    private InfringementClueMapper infringementClueMapper;

    @Autowired
    private AppealRecordMapper appealRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("assetCount", imageAssetMapper.selectCount(null));
        result.put("taskCount", auditTaskMapper.selectCount(null));
        result.put("registerCount", copyrightRegisterMapper.selectCount(null));
        result.put("evidenceCount", evidenceRecordMapper.selectCount(null));
        result.put("pendingAppeal", appealRecordMapper.selectCount(new LambdaQueryWrapper<AppealRecord>().eq(AppealRecord::getStatus, 0)));
        result.put("pendingClue", infringementClueMapper.selectCount(new LambdaQueryWrapper<InfringementClue>().eq(InfringementClue::getStatus, 0)));
        result.put("publishedAssets", imageAssetMapper.selectCount(new LambdaQueryWrapper<ImageAsset>().eq(ImageAsset::getStatus, 1)));
        result.put("approvedRegisters", copyrightRegisterMapper.selectCount(new LambdaQueryWrapper<CopyrightRegister>().eq(CopyrightRegister::getRegisterStatus, 1)));
        result.put("validEvidence", evidenceRecordMapper.selectCount(new LambdaQueryWrapper<EvidenceRecord>().eq(EvidenceRecord::getEvidenceStatus, 1)));
        result.put("highRisk", auditResultMapper.selectCount(new LambdaQueryWrapper<AuditResult>().eq(AuditResult::getRiskLevel, "高")));
        List<AuditResult> results = auditResultMapper.selectList(null);
        BigDecimal total = BigDecimal.ZERO;
        for (AuditResult item : results) {
            total = total.add(item.getScore() == null ? BigDecimal.ZERO : item.getScore());
        }
        result.put("averageScore", results.isEmpty() ? BigDecimal.ZERO : total.divide(new BigDecimal(results.size()), 2, RoundingMode.HALF_UP));
        return result;
    }
}
