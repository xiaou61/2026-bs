package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void addComment(Comment comment);
    List<Comment> getProductComments(Long productId);
}

