package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.AuditResult;
import com.aigccopyright.entity.AuditRule;
import com.aigccopyright.entity.AuditTask;
import com.aigccopyright.entity.ImageAsset;
import com.aigccopyright.mapper.AuditResultMapper;
import com.aigccopyright.mapper.AuditRuleMapper;
import com.aigccopyright.mapper.AuditTaskMapper;
import com.aigccopyright.mapper.ImageAssetMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuditTaskService extends ServiceImpl<AuditTaskMapper, AuditTask> {

    @Autowired
    private AuditResultMapper auditResultMapper;

    @Autowired
    private AuditRuleMapper auditRuleMapper;

    @Autowired
    private ImageAssetMapper imageAssetMapper;

    public Page<AuditTask> page(Integer pageNum, Integer pageSize, String keyword, Integer status, String priority) {
        LambdaQueryWrapper<AuditTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(AuditTask::getTaskNo, keyword).or().like(AuditTask::getTaskName, keyword));
        wrapper.eq(status != null, AuditTask::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), AuditTask::getPriority, priority);
        wrapper.orderByDesc(AuditTask::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(AuditTask entity, Long userId) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getTaskName())) {
            throw new BusinessException(400, "审核任务信息不完整");
        }
        if (entity.getId() == null) {
            entity.setTaskNo("AT" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999));
            entity.setStatus(0);
            entity.setAuditorId(userId);
            entity.setCreateTime(LocalDateTime.now());
            entity.setPriority(StringUtils.hasText(entity.getPriority()) ? entity.getPriority() : "中");
        }
        saveOrUpdate(entity);
    }

    public void run(Long id) {
        AuditTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "审核任务不存在");
        }
        ImageAsset asset = imageAssetMapper.selectById(task.getAssetId());
        if (asset == null) {
            throw new BusinessException(400, "图片作品不存在");
        }
        List<AuditRule> rules = auditRuleMapper.selectList(new LambdaQueryWrapper<AuditRule>().eq(AuditRule::getStatus, 1));
        List<AuditRule> matched = rules.stream().filter(rule -> match(asset, rule)).collect(Collectors.toList());
        String riskLevel = matched.stream().anyMatch(rule -> "高".equals(rule.getRiskLevel())) ? "高" : matched.isEmpty() ? "低" : "中";
        BigDecimal score = "高".equals(riskLevel) ? new BigDecimal("62.00") : "中".equals(riskLevel) ? new BigDecimal("81.00") : new BigDecimal("95.00");
        auditResultMapper.delete(new LambdaQueryWrapper<AuditResult>().eq(AuditResult::getTaskId, id));
        AuditResult result = new AuditResult();
        result.setTaskId(id);
        result.setAssetId(asset.getId());
        result.setMatchedRules(matched.isEmpty() ? "未命中高危规则" : matched.stream().map(AuditRule::getRuleName).collect(Collectors.joining(",")));
        result.setRiskLevel(riskLevel);
        result.setScore(score);
        result.setConclusion("高".equals(riskLevel) ? "建议驳回" : "中".equals(riskLevel) ? "需人工复核" : "审核通过");
        result.setSuggestion("高".equals(riskLevel) ? "建议修改提示词或补充版权证明" : "中".equals(riskLevel) ? "建议审核员复核细节" : "可进入版权登记流程");
        result.setReviewStatus(0);
        result.setCreateTime(LocalDateTime.now());
        result.setUpdateTime(LocalDateTime.now());
        auditResultMapper.insert(result);
        task.setStatus(1);
        updateById(task);
        asset.setStatus("高".equals(riskLevel) ? 3 : 1);
        asset.setUpdateTime(LocalDateTime.now());
        imageAssetMapper.updateById(asset);
    }

    public void finish(Long id) {
        AuditTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "审核任务不存在");
        }
        task.setStatus(2);
        task.setFinishTime(LocalDateTime.now());
        updateById(task);
    }

    public void reject(Long id) {
        AuditTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "审核任务不存在");
        }
        task.setStatus(3);
        task.setFinishTime(LocalDateTime.now());
        updateById(task);
    }

    private boolean match(ImageAsset asset, AuditRule rule) {
        if (!StringUtils.hasText(rule.getKeywords())) {
            return false;
        }
        String text = (asset.getTitle() + " " + asset.getPromptText() + " " + asset.getDescription()).toLowerCase();
        for (String keyword : rule.getKeywords().split(",")) {
            if (StringUtils.hasText(keyword) && text.contains(keyword.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
