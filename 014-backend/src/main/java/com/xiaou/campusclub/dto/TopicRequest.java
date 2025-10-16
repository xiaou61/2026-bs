package com.xiaou.campusclub.dto;

import lombok.Data;

@Data
public class TopicRequest {
    private Long clubId;
    private Long circleId;
    private String title;
    private String content;
    private String images;
}

