package com.bike.mapper;

import com.bike.entity.PricingRule;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PricingRuleMapper {
    List<PricingRule> findList();
    PricingRule findById(@Param("id") Long id);
    PricingRule findByBikeType(@Param("bikeType") Integer bikeType);
    int insert(PricingRule rule);
    int update(PricingRule rule);
    int deleteById(@Param("id") Long id);
}
