package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("consumable_catalog")
public class CounselCase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String consumableNo;
    private String consumableName;
    private String specModel;
    private String unitName;
    private Integer safeStock;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







