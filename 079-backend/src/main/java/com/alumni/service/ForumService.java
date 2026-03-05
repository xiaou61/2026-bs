package com.alumni.service;

import com.alumni.entity.*;
import com.alumni.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ForumService {

    @Autowired
    private ForumCategoryMapper forumCategoryMapper;

    @Autowired
    private ForumPostMapper forumPostMapper;

    @Autowired
    private ForumReplyMapper forumReplyMapper;

    @Autowired
    private ForumLikeMapper forumLikeMapper;

    @Autowired
    private UserMapper userMapper;

    public List<ForumCategory> categoryList() {
        return forumCategoryMapper.selectList(new LambdaQueryWrapper<ForumCategory>().orderByAsc(ForumCategory::getSort));
    }

    public void addCategory(ForumCategory category) {
        category.setPostCount(0);
        forumCategoryMapper.insert(category);
    }

    public void updateCategory(ForumCategory category) {
        forumCategoryMapper.updateById(category);
    }

    public void deleteCategory(Long id) {
        forumCategoryMapper.deleteById(id);
    }

    public Page<ForumPost> postList(Integer pageNum, Integer pageSize, Long categoryId, String title, Long userId) {
        Page<ForumPost> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ForumPost> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(ForumPost::getCategoryId, categoryId);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like(ForumPost::getTitle, title);
        }
        wrapper.eq(ForumPost::getStatus, 0);
        wrapper.orderByDesc(ForumPost::getIsTop).orderByDesc(ForumPost::getCreateTime);
        Page<ForumPost> result = forumPostMapper.selectPage(page, wrapper);
        List<User> users = userMapper.selectList(null);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        List<ForumCategory> categories = forumCategoryMapper.selectList(null);
        Map<Long, String> categoryMap = categories.stream().collect(Collectors.toMap(ForumCategory::getId, ForumCategory::getName));
        List<ForumLike> likes = userId != null ? forumLikeMapper.selectList(new LambdaQueryWrapper<ForumLike>().eq(ForumLike::getUserId, userId)) : List.of();
        Map<Long, Boolean> likeMap = likes.stream().collect(Collectors.toMap(ForumLike::getPostId, l -> true));
        result.getRecords().forEach(p -> {
            User user = userMap.get(p.getUserId());
            if (user != null) {
                p.setUserName(user.getName());
                p.setUserAvatar(user.getAvatar());
            }
            p.setCategoryName(categoryMap.get(p.getCategoryId()));
            p.setLiked(likeMap.getOrDefault(p.getId(), false));
        });
        return result;
    }

    public ForumPost getPostById(Long id, Long userId) {
        ForumPost post = forumPostMapper.selectById(id);
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            forumPostMapper.updateById(post);
            User user = userMapper.selectById(post.getUserId());
            if (user != null) {
                post.setUserName(user.getName());
                post.setUserAvatar(user.getAvatar());
            }
            ForumCategory category = forumCategoryMapper.selectById(post.getCategoryId());
            if (category != null) post.setCategoryName(category.getName());
            if (userId != null) {
                ForumLike like = forumLikeMapper.selectOne(new LambdaQueryWrapper<ForumLike>()
                        .eq(ForumLike::getPostId, id).eq(ForumLike::getUserId, userId));
                post.setLiked(like != null);
            }
        }
        return post;
    }

    public void addPost(ForumPost post) {
        post.setViewCount(0);
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setIsTop(0);
        post.setIsEssence(0);
        post.setStatus(0);
        forumPostMapper.insert(post);
        ForumCategory category = forumCategoryMapper.selectById(post.getCategoryId());
        if (category != null) {
            category.setPostCount(category.getPostCount() + 1);
            forumCategoryMapper.updateById(category);
        }
    }

    public void updatePost(ForumPost post) {
        forumPostMapper.updateById(post);
    }

    public void deletePost(Long id) {
        ForumPost post = forumPostMapper.selectById(id);
        if (post != null) {
            post.setStatus(1);
            forumPostMapper.updateById(post);
        }
    }

    public List<ForumReply> getPostReplies(Long postId) {
        List<ForumReply> replies = forumReplyMapper.selectList(
                new LambdaQueryWrapper<ForumReply>().eq(ForumReply::getPostId, postId).orderByAsc(ForumReply::getCreateTime));
        List<User> users = userMapper.selectList(null);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        replies.forEach(r -> {
            User user = userMap.get(r.getUserId());
            if (user != null) {
                r.setUserName(user.getName());
                r.setUserAvatar(user.getAvatar());
            }
            if (r.getReplyToId() != null) {
                ForumReply replyTo = forumReplyMapper.selectById(r.getReplyToId());
                if (replyTo != null) {
                    User toUser = userMap.get(replyTo.getUserId());
                    if (toUser != null) r.setReplyToName(toUser.getName());
                }
            }
        });
        return replies;
    }

    public void addReply(ForumReply reply) {
        forumReplyMapper.insert(reply);
        ForumPost post = forumPostMapper.selectById(reply.getPostId());
        if (post != null) {
            post.setReplyCount(post.getReplyCount() + 1);
            post.setLastReplyTime(LocalDateTime.now());
            forumPostMapper.updateById(post);
        }
    }

    public void deleteReply(Long id) {
        ForumReply reply = forumReplyMapper.selectById(id);
        if (reply != null) {
            forumReplyMapper.deleteById(id);
            ForumPost post = forumPostMapper.selectById(reply.getPostId());
            if (post != null && post.getReplyCount() > 0) {
                post.setReplyCount(post.getReplyCount() - 1);
                forumPostMapper.updateById(post);
            }
        }
    }

    public void like(Long postId, Long userId) {
        ForumLike exist = forumLikeMapper.selectOne(new LambdaQueryWrapper<ForumLike>()
                .eq(ForumLike::getPostId, postId).eq(ForumLike::getUserId, userId));
        if (exist == null) {
            ForumLike like = new ForumLike();
            like.setPostId(postId);
            like.setUserId(userId);
            forumLikeMapper.insert(like);
            ForumPost post = forumPostMapper.selectById(postId);
            if (post != null) {
                post.setLikeCount(post.getLikeCount() + 1);
                forumPostMapper.updateById(post);
            }
        }
    }

    public void unlike(Long postId, Long userId) {
        ForumLike like = forumLikeMapper.selectOne(new LambdaQueryWrapper<ForumLike>()
                .eq(ForumLike::getPostId, postId).eq(ForumLike::getUserId, userId));
        if (like != null) {
            forumLikeMapper.deleteById(like.getId());
            ForumPost post = forumPostMapper.selectById(postId);
            if (post != null && post.getLikeCount() > 0) {
                post.setLikeCount(post.getLikeCount() - 1);
                forumPostMapper.updateById(post);
            }
        }
    }
}
