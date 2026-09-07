package com.xiaou.ailearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推荐记录实体类
 */
@Data
@TableName("recommendation")
public class Recommendation {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private Long userId;
    
    /**
     * 推荐内容类型：1-课程，2-知识点，3-题目
     */
    private Integer contentType;
    
    private Long contentId;
    
    /**
     * 推荐类型：1-协同过滤，2-内容推荐，3-热门推荐
     */
    private Integer recommendationType;
    
    /**
     * 推荐分数
     */
    private Double recommendationScore;
    
    /**
     * 算法版本
     */
    private String algorithmVersion;
    
    /**
     * 是否点击
     */
    private Boolean isClicked;
    
    /**
     * 是否完成学习
     */
    private Boolean isCompleted;
    
    /**
     * 用户反馈分数(1-5)
     */
    private Integer feedbackScore;
    
    private LocalDateTime createTime;
}