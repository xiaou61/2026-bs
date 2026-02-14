package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.RiskRule;
import com.fraud.mapper.RiskRuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class RiskRuleService {

    @Resource
    private RiskRuleMapper riskRuleMapper;

    public List<RiskRule> list() {
        return riskRuleMapper.selectList(new QueryWrapper<RiskRule>()
                .eq("status", 1)
                .orderByDesc("id"));
    }

    public Page<RiskRule> page(Integer pageNum, Integer pageSize, String ruleName, String ruleType, Integer status) {
        QueryWrapper<RiskRule> wrapper = new QueryWrapper<>();
        if (ruleName != null && !ruleName.trim().isEmpty()) {
            wrapper.like("rule_name", ruleName.trim());
        }
        if (ruleType != null && !ruleType.trim().isEmpty()) {
            wrapper.eq("rule_type", ruleType.trim().toUpperCase());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("id");
        return riskRuleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(RiskRule rule) {
        if (rule == null || rule.getRuleName() == null || rule.getRuleName().trim().isEmpty()) {
            throw new BusinessException("规则名称不能为空");
        }
        if (rule.getRuleCode() == null || rule.getRuleCode().trim().isEmpty()) {
            throw new BusinessException("规则编码不能为空");
        }
        if (rule.getRuleType() == null || rule.getRuleType().trim().isEmpty()) {
            throw new BusinessException("规则类型不能为空");
        }
        rule.setRuleName(rule.getRuleName().trim());
        rule.setRuleCode(rule.getRuleCode().trim().toUpperCase());
        rule.setRuleType(rule.getRuleType().trim().toUpperCase());
        if (rule.getRuleName().length() > 80) {
            throw new BusinessException("规则名称不能超过80字符");
        }
        if (rule.getRuleCode().length() > 50) {
            throw new BusinessException("规则编码不能超过50字符");
        }
        if (!Arrays.asList("AMOUNT", "FREQUENCY").contains(rule.getRuleType())) {
            throw new BusinessException("规则类型不合法");
        }
        if (rule.getThreshold() == null || rule.getThreshold().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("阈值不合法");
        }
        if (rule.getWeight() == null || rule.getWeight() <= 0 || rule.getWeight() > 100) {
            throw new BusinessException("权重范围为1-100");
        }
        if (rule.getDescription() != null) {
            rule.setDescription(rule.getDescription().trim());
            if (rule.getDescription().length() > 255) {
                throw new BusinessException("描述不能超过255字符");
            }
        }
        if (rule.getStatus() == null) {
            rule.setStatus(1);
        }
        if (rule.getStatus() != 0 && rule.getStatus() != 1) {
            throw new BusinessException("状态不合法");
        }
        if (rule.getId() == null) {
            Long count = riskRuleMapper.selectCount(new QueryWrapper<RiskRule>().eq("rule_code", rule.getRuleCode()));
            if (count != null && count > 0) {
                throw new BusinessException("规则编码已存在");
            }
            if (rule.getHitCount() == null) {
                rule.setHitCount(0);
            }
            riskRuleMapper.insert(rule);
        } else {
            RiskRule db = riskRuleMapper.selectById(rule.getId());
            if (db == null) {
                throw new BusinessException("规则不存在");
            }
            Long count = riskRuleMapper.selectCount(new QueryWrapper<RiskRule>()
                    .eq("rule_code", rule.getRuleCode())
                    .ne("id", rule.getId()));
            if (count != null && count > 0) {
                throw new BusinessException("规则编码已存在");
            }
            if (rule.getHitCount() == null) {
                rule.setHitCount(db.getHitCount());
            }
            riskRuleMapper.updateById(rule);
        }
    }

    public void deleteById(Long id) {
        riskRuleMapper.deleteById(id);
    }

    public List<RiskRule> activeRules() {
        return riskRuleMapper.selectList(new QueryWrapper<RiskRule>()
                .eq("status", 1)
                .orderByAsc("id"));
    }

    public void increaseHitCount(Long ruleId) {
        if (ruleId == null) {
            return;
        }
        RiskRule rule = riskRuleMapper.selectById(ruleId);
        if (rule == null) {
            return;
        }
        Integer hitCount = rule.getHitCount() == null ? 0 : rule.getHitCount();
        rule.setHitCount(hitCount + 1);
        riskRuleMapper.updateById(rule);
    }
}
