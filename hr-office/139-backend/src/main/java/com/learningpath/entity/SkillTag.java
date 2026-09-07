package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("skill_tag")
public class SkillTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tagNo;
    private String tagName;
    private String categoryName;
    private String tagLevel;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



