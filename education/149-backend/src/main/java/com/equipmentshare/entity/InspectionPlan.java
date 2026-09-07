package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inspection_plan")
public class InspectionPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String inspectionNo;
    private String equipmentName;
    private String inspectionTheme;
    private String inspectionContent;
    private String inspectionTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








