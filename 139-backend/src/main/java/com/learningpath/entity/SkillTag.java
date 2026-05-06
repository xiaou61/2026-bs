package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("outbound_record")
public class SkillTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String outboundNo;
    private String consumableName;
    private String labName;
    private Integer outboundQty;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



