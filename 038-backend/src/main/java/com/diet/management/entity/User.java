package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.diet.management.enums.Enums;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@TableName("users")
public class User implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String avatar;
    
    private Enums.Gender gender;
    
    private Integer age;
    
    private BigDecimal height;
    
    private BigDecimal weight;
    
    private String email;
    
    private String phone;
    
    private Enums.UserRole role;
    
    private Integer status;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
