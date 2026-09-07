package com.xiaou.herbal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.herbal.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void createComment(Long userId, Comment comment);

    List<Comment> getCommentsByTarget(Integer targetType, Long targetId);

    void deleteComment(Long commentId);

    void likeComment(Long commentId);

    List<Comment> getUserComments(Long userId);
}
