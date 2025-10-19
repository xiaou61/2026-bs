package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.confession.entity.Post;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.PostMapper;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.util.AnonymousNicknameGenerator;
import com.xiaou.confession.util.SensitiveWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final SensitiveWordFilter sensitiveWordFilter;
    
    @Transactional
    public Post createPost(Long userId, String category, String title, String content, String images, String tags) {
        User user = userMapper.selectById(userId);
        if (user.getAuthStatus() != 2) {
            throw new RuntimeException("请先完成实名认证");
        }
        
        List<String> sensitiveWords = sensitiveWordFilter.getSensitiveWords(content);
        String filteredContent = sensitiveWordFilter.filterSensitiveWord(content, "*");
        
        Post post = new Post();
        post.setUserId(userId);
        post.setAnonymousNickname(AnonymousNicknameGenerator.generate());
        post.setAnonymousAvatar(AnonymousNicknameGenerator.generateAvatar());
        post.setCategory(category);
        post.setTitle(title);
        post.setContent(filteredContent);
        post.setImages(images);
        post.setTags(tags);
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setHotScore(BigDecimal.ZERO);
        post.setIsTop(0);
        
        if (!sensitiveWords.isEmpty() && containsHighLevelSensitiveWord(sensitiveWords)) {
            post.setStatus(0);
            post.setSensitiveWords(String.join(",", sensitiveWords));
        } else {
            post.setStatus(1);
        }
        
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        
        postMapper.insert(post);
        
        post.setPostNo("#" + String.format("%05d", post.getId()));
        postMapper.updateById(post);
        
        user.setPostCount(user.getPostCount() + 1);
        user.setPoints(user.getPoints() + 5);
        userMapper.updateById(user);
        
        return post;
    }
    
    public IPage<Post> getPostList(Integer page, Integer size, String category, String orderBy) {
        Page<Post> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1);
        
        if (category != null && !category.equals("ALL")) {
            wrapper.eq(Post::getCategory, category);
        }
        
        wrapper.orderByDesc(Post::getIsTop);
        
        if ("HOT".equals(orderBy)) {
            wrapper.orderByDesc(Post::getHotScore);
        } else {
            wrapper.orderByDesc(Post::getCreateTime);
        }
        
        return postMapper.selectPage(pageParam, wrapper);
    }
    
    public Post getPostDetail(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() != 1) {
            throw new RuntimeException("帖子不存在或已被删除");
        }
        
        post.setViewCount(post.getViewCount() + 1);
        postMapper.updateById(post);
        
        return post;
    }
    
    public IPage<Post> searchPosts(Integer page, Integer size, String keyword) {
        Page<Post> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1);
        wrapper.and(w -> w.like(Post::getTitle, keyword)
                .or().like(Post::getContent, keyword)
                .or().like(Post::getTags, keyword));
        wrapper.orderByDesc(Post::getCreateTime);
        
        return postMapper.selectPage(pageParam, wrapper);
    }
    
    public IPage<Post> getMyPosts(Long userId, Integer page, Integer size) {
        Page<Post> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId, userId);
        wrapper.orderByDesc(Post::getCreateTime);
        
        return postMapper.selectPage(pageParam, wrapper);
    }
    
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        
        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("无权删除");
        }
        
        post.setStatus(2);
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
    }
    
    private boolean containsHighLevelSensitiveWord(List<String> words) {
        return false;
    }
}

