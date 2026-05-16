package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("community_activity")
public class CommunityActivity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String activityNo;
    private String projectName;
    private String activityTheme;
    private String activityLocation;
    private String activityTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

