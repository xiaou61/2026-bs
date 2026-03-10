package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("check_package")
public class CheckPackage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String packageName;
    private String packageCode;
    private BigDecimal price;
    private String description;
    private String suitableCrowd;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Long> itemIds;
}
