package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Comment;
import com.ticket.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MovieService movieService;

    public Page<Comment> getMovieComments(Long movieId, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("movie_id", movieId)
                .eq("status", "approved")
                .orderByDesc("create_time");
        return commentMapper.selectPage(page, wrapper);
    }

    public void addComment(Comment comment, Long userId) {
        comment.setUserId(userId);
        comment.setStatus("approved");
        commentMapper.insert(comment);

        if (comment.getRating() != null) {
            movieService.updateRating(comment.getMovieId(), comment.getRating());
        }
    }

    public void deleteComment(Long id, Long userId) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }

        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权删除");
        }

        commentMapper.deleteById(id);
    }

    public Page<Comment> listComments(Integer pageNum, Integer pageSize, String status) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return commentMapper.selectPage(page, wrapper);
    }

    public void updateStatus(Long id, String status) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        commentMapper.updateById(comment);
    }
}
