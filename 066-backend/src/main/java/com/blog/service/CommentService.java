package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.common.PageResult;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.PostMapper;
import com.blog.mapper.UserMapper;
import com.blog.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PostService postService;

    public PageResult<CommentVO> postPage(Long postId, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> resultPage = commentMapper.selectPage(page, new LambdaQueryWrapper<Comment>()
                .eq(Comment::getPostId, postId)
                .eq(Comment::getStatus, 1)
                .orderByDesc(Comment::getId));
        PageResult<CommentVO> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildCommentVOList(resultPage.getRecords()));
        return result;
    }

    public PageResult<CommentVO> page(Integer pageNum, Integer pageSize, Long postId, Integer status, String keyword, String role, Long userId) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> resultPage = commentMapper.selectPage(page, new LambdaQueryWrapper<Comment>()
                .eq(postId != null, Comment::getPostId, postId)
                .eq(status != null, Comment::getStatus, status)
                .eq("USER".equals(role), Comment::getUserId, userId)
                .like(StringUtils.hasText(keyword), Comment::getContent, keyword)
                .orderByDesc(Comment::getId));
        PageResult<CommentVO> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildCommentVOList(resultPage.getRecords()));
        return result;
    }

    public void add(Comment comment, Long userId) {
        if (comment == null || comment.getPostId() == null || !StringUtils.hasText(comment.getContent())) {
            throw new BusinessException("评论参数不完整");
        }
        Post post = postMapper.selectById(comment.getPostId());
        if (post == null || post.getStatus() == null || post.getStatus() != 1) {
            throw new BusinessException("文章不存在或未发布");
        }
        comment.setUserId(userId);
        comment.setContent(comment.getContent().trim());
        comment.setStatus(0);
        comment.setReplyContent(null);
        commentMapper.insert(comment);
    }

    public void review(Long id, Integer status, String replyContent) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("审核状态不合法");
        }
        Comment db = commentMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("评论不存在");
        }
        Integer before = db.getStatus() == null ? 0 : db.getStatus();
        db.setStatus(status);
        db.setReplyContent(StringUtils.hasText(replyContent) ? replyContent.trim() : "");
        commentMapper.updateById(db);
        if (before != 1 && status == 1) {
            postService.increaseCommentCount(db.getPostId(), 1);
        }
        if (before == 1 && status == 2) {
            postService.increaseCommentCount(db.getPostId(), -1);
        }
    }

    public void deleteById(Long id, Long userId, String role) {
        Comment db = commentMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("USER".equals(role) && !db.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }
        if (db.getStatus() != null && db.getStatus() == 1) {
            postService.increaseCommentCount(db.getPostId(), -1);
        }
        commentMapper.deleteById(id);
    }

    public Long countAll() {
        return commentMapper.selectCount(null);
    }

    private List<CommentVO> buildCommentVOList(List<Comment> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> postIds = new HashSet<>();
        Set<Long> userIds = new HashSet<>();
        for (Comment item : list) {
            postIds.add(item.getPostId());
            userIds.add(item.getUserId());
        }
        Map<Long, String> postTitleMap = new HashMap<>();
        if (!postIds.isEmpty()) {
            List<Post> posts = postMapper.selectBatchIds(postIds);
            for (Post post : posts) {
                postTitleMap.put(post.getId(), post.getTitle());
            }
        }
        Map<Long, String> userNameMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            for (User user : users) {
                userNameMap.put(user.getId(), StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername());
            }
        }
        List<CommentVO> result = new ArrayList<>();
        for (Comment item : list) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(item, vo);
            vo.setPostTitle(postTitleMap.getOrDefault(item.getPostId(), ""));
            vo.setUserName(userNameMap.getOrDefault(item.getUserId(), ""));
            result.add(vo);
        }
        return result;
    }
}
