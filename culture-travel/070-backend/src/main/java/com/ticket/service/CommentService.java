package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Comment;
import com.ticket.entity.Movie;
import com.ticket.entity.TicketOrder;
import com.ticket.mapper.CommentMapper;
import com.ticket.mapper.MovieMapper;
import com.ticket.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieService movieService;

    public PageResult<Comment> publicPage(Long movieId, Integer pageNum, Integer pageSize) {
        return page(movieId, "APPROVED", pageNum, pageSize);
    }

    public PageResult<Comment> page(Long movieId, String status, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>()
                .eq(movieId != null, Comment::getMovieId, movieId)
                .eq(StringUtils.hasText(status), Comment::getStatus, status == null ? null : status.trim())
                .orderByDesc(Comment::getId);
        Page<Comment> resultPage = commentMapper.selectPage(page, wrapper);
        PageResult<Comment> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void add(Long userId, Comment comment) {
        if (comment == null || comment.getMovieId() == null || comment.getRating() == null || !StringUtils.hasText(comment.getContent())) {
            throw new BusinessException("评论参数不完整");
        }
        if (comment.getRating() < 1 || comment.getRating() > 10) {
            throw new BusinessException("评分范围1-10");
        }
        Movie movie = movieMapper.selectById(comment.getMovieId());
        if (movie == null) {
            throw new BusinessException("影片不存在");
        }
        if (comment.getOrderId() != null) {
            TicketOrder order = ticketOrderMapper.selectById(comment.getOrderId());
            if (order == null || !order.getUserId().equals(userId)) {
                throw new BusinessException("订单不存在");
            }
            if (!OrderService.STATUS_PAID.equals(order.getStatus()) && !OrderService.STATUS_FINISHED.equals(order.getStatus())) {
                throw new BusinessException("订单状态不可评价");
            }
        }
        comment.setUserId(userId);
        comment.setContent(comment.getContent().trim());
        comment.setStatus("PENDING");
        commentMapper.insert(comment);
        movieService.refreshStats(comment.getMovieId());
    }

    public void audit(Long id, String status) {
        if (!"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            throw new BusinessException("审核状态不合法");
        }
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        comment.setStatus(status);
        commentMapper.updateById(comment);
        movieService.refreshStats(comment.getMovieId());
    }

    public void deleteById(Long id, Long userId, String role) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        if (!"ADMIN".equals(role) && !comment.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限");
        }
        commentMapper.deleteById(id);
        movieService.refreshStats(comment.getMovieId());
    }
}
