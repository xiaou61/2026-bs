package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("visit")
public class Visit {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private String visitorName;
    private String visitorPhone;
    private String relationship;
    private LocalDate visitDate;
    private String visitTime;
    private Integer visitorCount;
    private String purpose;
    private Integer status;  // 0-待审核 1-已通过 2-已拒绝 3-已完成 4-已取消
    private Long applyUserId;
    private Long approveUserId;
    private LocalDateTime approveTime;
    private String approveRemark;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
