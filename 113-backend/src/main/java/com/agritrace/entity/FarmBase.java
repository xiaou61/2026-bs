package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("farm_base")
public class FarmBase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String baseName;
    private String baseCode;
    private String regionName;
    private Integer areaMu;
    private String ownerName;
    private String certStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
