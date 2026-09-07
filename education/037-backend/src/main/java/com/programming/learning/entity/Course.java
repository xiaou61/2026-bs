package com.programming.learning.entity;

import com.programming.learning.enums.CourseLevel;
import com.programming.learning.enums.CourseStatus;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 课程实体
 */
@Data
public class Course {
    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 课程简介
     */
    private String description;

    /**
     * 课程分类
     */
    private String category;

    /**
     * 课程难度
     */
    private CourseLevel level;

    /**
     * 课程状态
     */
    private CourseStatus status;

    /**
     * 授课教师ID
     */
    private Long teacherId;

    /**
     * 授课教师姓名
     */
    private String teacherName;

    /**
     * 学习人数
     */
    private Integer studentCount;

    /**
     * 章节数
     */
    private Integer chapterCount;

    /**
     * 课程时长（分钟）
     */
    private Integer duration;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer favoriteCount;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
