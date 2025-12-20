package com.xiaou.rice.modules.farm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("farm_plot")
public class FarmPlot extends BaseEntity {
    private Long userId;
    private String name;
    private BigDecimal area; // 单位：亩
    private String cropVariety;
    private String growthStage;
    private String location;
    private Double latitude;
    private Double longitude;
    private String photos; // JSON数组字符串
    private String remark;
}
