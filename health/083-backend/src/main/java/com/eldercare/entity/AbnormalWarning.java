package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("abnormal_warning")
public class AbnormalWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long resultId;
    private Long elderId;
    private Long itemId;
    private String warningLevel;
    private String warningContent;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
