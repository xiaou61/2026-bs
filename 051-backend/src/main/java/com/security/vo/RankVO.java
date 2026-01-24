package com.security.vo;

import lombok.Data;

@Data
public class RankVO {
    private Integer rank;
    private Long userId;
    private String nickname;
    private String avatar;
    private Integer points;
}
