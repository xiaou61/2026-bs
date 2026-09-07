package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("elder")
public class Elder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer gender;  // 1-男 2-女
    private LocalDate birthday;
    private String idCard;
    private String phone;
    private String photo;
    private Long bedId;
    private Integer careLevel;  // 1-自理 2-半护理 3-全护理 4-特护
    private String healthStatus;
    private String allergies;
    private String emergencyContact;
    private String emergencyPhone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer status;  // 0-已退住 1-在住
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
