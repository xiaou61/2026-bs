package com.security.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QaPostVO {
    private Long id;
    private Long userId;
    private String nickname;
    private String avatar;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer replyCount;
    private LocalDateTime createTime;
    private List<QaReplyVO> replies;
}
