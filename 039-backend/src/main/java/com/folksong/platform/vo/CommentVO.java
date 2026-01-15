package com.folksong.platform.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long songId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer status;
    private List<CommentVO> replies;
    private LocalDateTime createTime;
}
