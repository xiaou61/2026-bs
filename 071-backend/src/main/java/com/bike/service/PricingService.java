package com.bike.service;

import com.bike.entity.PricingRule;
import com.bike.mapper.PricingRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    @Autowired
    private PricingRuleMapper pricingRuleMapper;

    public List<PricingRule> getList() {
        return pricingRuleMapper.findList();
    }

    public void add(PricingRule rule) {
        if (rule.getStatus() == null) {
            rule.setStatus(1);
        }
        pricingRuleMapper.insert(rule);
    }

    public void update(PricingRule rule) {
        pricingRuleMapper.update(rule);
    }

    public void delete(Long id) {
        pricingRuleMapper.deleteById(id);
    }
}
