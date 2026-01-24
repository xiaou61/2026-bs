package com.security.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QaReplyVO {
    private Long id;
    private Long postId;
    private Long userId;
    private String nickname;
    private String avatar;
    private String content;
    private Integer likeCount;
    private LocalDateTime createTime;
}
