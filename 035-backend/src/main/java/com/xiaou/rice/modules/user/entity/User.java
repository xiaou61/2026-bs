package com.xiaou.rice.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

@Data
@TableName("user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String nickname;
    /** 1=农户 2=机手 9=管理员 */
    private Integer role;
    /** 0=禁用 1=正常 */
    private Integer status;
}
