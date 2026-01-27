package com.online.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.Comment;
import com.online.entity.Course;
import com.online.entity.User;
import com.online.mapper.CommentMapper;
import com.online.mapper.CourseMapper;
import com.online.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    public Page<Comment> getList(Long courseId, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> result = this.page(page, new LambdaQueryWrapper<Comment>()
                .eq(Comment::getCourseId, courseId)
                .eq(Comment::getStatus, 1)
                .orderByDesc(Comment::getCreateTime));
        result.getRecords().forEach(this::fillUserInfo);
        return result;
    }

    public void addComment(Long userId, Long courseId, String content, Integer score) {
        Comment exist = this.getOne(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getUserId, userId)
                .eq(Comment::getCourseId, courseId));
        if (exist != null) {
            throw new RuntimeException("您已评价过该课程");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setCourseId(courseId);
        comment.setContent(content);
        comment.setScore(score);
        comment.setStatus(1);
        this.save(comment);
        updateCourseScore(courseId);
    }

    private void updateCourseScore(Long courseId) {
        List<Comment> comments = this.list(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getCourseId, courseId)
                .eq(Comment::getStatus, 1));
        if (!comments.isEmpty()) {
            double avg = comments.stream().mapToInt(Comment::getScore).average().orElse(5.0);
            Course course = courseMapper.selectById(courseId);
            course.setScore(BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP));
            courseMapper.updateById(course);
        }
    }

    private void fillUserInfo(Comment comment) {
        User user = userMapper.selectById(comment.getUserId());
        if (user != null) {
            comment.setUsername(user.getNickname());
            comment.setAvatar(user.getAvatar());
        }
    }
}
