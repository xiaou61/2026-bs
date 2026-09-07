package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Comment;
import com.xiaou.mapper.CommentMapper;
import com.xiaou.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public void addComment(Comment comment) {
        this.save(comment);
    }

    @Override
    public List<Comment> getProductComments(Long productId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getProductId, productId);
        wrapper.orderByDesc(Comment::getCreateTime);
        return this.list(wrapper);
    }
}

