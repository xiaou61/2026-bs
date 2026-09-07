package com.teachres.mapper;

import com.teachres.entity.EvalIndicator;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EvalIndicatorMapper {
    EvalIndicator selectById(@Param("id") Long id);

    List<EvalIndicator> selectList(@Param("indicatorName") String indicatorName,
                                   @Param("status") Integer status);

    List<EvalIndicator> selectEnabledList();

    BigDecimal sumEnabledWeight();

    int insert(EvalIndicator indicator);

    int update(EvalIndicator indicator);

    int deleteById(@Param("id") Long id);
}
