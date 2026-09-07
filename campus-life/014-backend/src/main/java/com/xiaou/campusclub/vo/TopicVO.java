package com.xiaou.campusclub.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopicVO {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private Long clubId;
    private String clubName;
    private Long circleId;
    private String circleName;
    private String title;
    private String content;
    private List<String> images;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isLiked;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

