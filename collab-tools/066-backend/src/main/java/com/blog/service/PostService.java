package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.common.PageResult;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.PostTag;
import com.blog.entity.User;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.PostMapper;
import com.blog.mapper.PostTagMapper;
import com.blog.mapper.UserMapper;
import com.blog.vo.PostVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Resource
    private PostMapper postMapper;

    @Resource
    private PostTagMapper postTagMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

    public PageResult<PostVO> publicPage(Integer pageNum, Integer pageSize, String keyword, Long categoryId) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                .eq(Post::getStatus, 1)
                .eq(categoryId != null, Post::getCategoryId, categoryId)
                .and(StringUtils.hasText(keyword), w -> w.like(Post::getTitle, keyword).or().like(Post::getSummary, keyword))
                .orderByDesc(Post::getIsTop)
                .orderByDesc(Post::getId);
        Page<Post> resultPage = postMapper.selectPage(page, wrapper);
        PageResult<PostVO> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildPostVOList(resultPage.getRecords()));
        return result;
    }

    public PageResult<PostVO> page(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status, String role, Long userId) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                .and(StringUtils.hasText(keyword), w -> w.like(Post::getTitle, keyword).or().like(Post::getSummary, keyword))
                .eq(categoryId != null, Post::getCategoryId, categoryId)
                .eq(status != null, Post::getStatus, status)
                .eq("USER".equals(role), Post::getAuthorId, userId)
                .orderByDesc(Post::getIsTop)
                .orderByDesc(Post::getId);
        Page<Post> resultPage = postMapper.selectPage(page, wrapper);
        PageResult<PostVO> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(buildPostVOList(resultPage.getRecords()));
        return result;
    }

    public PostVO detail(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null || post.getStatus() == null || post.getStatus() != 1) {
            throw new BusinessException("文章不存在或未发布");
        }
        long viewCount = post.getViewCount() == null ? 0L : post.getViewCount();
        Post update = new Post();
        update.setId(post.getId());
        update.setViewCount(viewCount + 1);
        postMapper.updateById(update);
        post.setViewCount(viewCount + 1);
        List<PostVO> list = buildPostVOList(java.util.Collections.singletonList(post));
        return list.isEmpty() ? null : list.get(0);
    }

    public void save(Post post, Long userId, String role) {
        if (post == null || !StringUtils.hasText(post.getTitle()) || !StringUtils.hasText(post.getContent())) {
            throw new BusinessException("文章标题和内容不能为空");
        }
        if (post.getCategoryId() != null && !categoryService.existsEnabled(post.getCategoryId())) {
            throw new BusinessException("分类不存在或已停用");
        }
        if (post.getId() == null) {
            add(post, userId, role);
        } else {
            update(post, userId, role);
        }
    }

    private void add(Post post, Long userId, String role) {
        post.setTitle(post.getTitle().trim());
        post.setContent(post.getContent().trim());
        post.setSummary(buildSummary(post.getSummary(), post.getContent()));
        post.setCover(StringUtils.hasText(post.getCover()) ? post.getCover().trim() : "");
        post.setAuthorId("USER".equals(role) ? userId : (post.getAuthorId() == null ? userId : post.getAuthorId()));
        post.setStatus(normalizePostStatus(post.getStatus(), 0));
        post.setIsTop("ADMIN".equals(role) ? normalizeTop(post.getIsTop(), 0) : 0);
        post.setViewCount(0L);
        post.setCommentCount(0);
        postMapper.insert(post);
        replacePostTags(post.getId(), post.getTagIds());
    }

    private void update(Post post, Long userId, String role) {
        Post db = postMapper.selectById(post.getId());
        if (db == null) {
            throw new BusinessException("文章不存在");
        }
        if ("USER".equals(role) && !db.getAuthorId().equals(userId)) {
            throw new BusinessException("无权限修改该文章");
        }
        db.setTitle(post.getTitle().trim());
        db.setContent(post.getContent().trim());
        db.setSummary(buildSummary(post.getSummary(), post.getContent()));
        db.setCover(StringUtils.hasText(post.getCover()) ? post.getCover().trim() : "");
        db.setCategoryId(post.getCategoryId());
        if (post.getStatus() != null) {
            db.setStatus(normalizePostStatus(post.getStatus(), db.getStatus()));
        }
        if ("ADMIN".equals(role)) {
            db.setIsTop(post.getIsTop() == null ? db.getIsTop() : normalizeTop(post.getIsTop(), db.getIsTop()));
            db.setAuthorId(post.getAuthorId() == null ? db.getAuthorId() : post.getAuthorId());
        }
        postMapper.updateById(db);
        replacePostTags(db.getId(), post.getTagIds());
    }

    public void updateStatus(Long id, Integer status, Long userId, String role) {
        Post db = postMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("文章不存在");
        }
        if ("USER".equals(role) && !db.getAuthorId().equals(userId)) {
            throw new BusinessException("无权限操作");
        }
        db.setStatus(normalizePostStatus(status, db.getStatus()));
        postMapper.updateById(db);
    }

    public void updateTop(Long id, Integer isTop) {
        Post db = postMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("文章不存在");
        }
        db.setIsTop(normalizeTop(isTop, db.getIsTop()));
        postMapper.updateById(db);
    }

    public void deleteById(Long id, Long userId, String role) {
        Post db = postMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("USER".equals(role) && !db.getAuthorId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }
        postMapper.deleteById(id);
        postTagMapper.delete(new LambdaQueryWrapper<PostTag>().eq(PostTag::getPostId, id));
        commentMapper.delete(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, id));
    }

    public Long countAll() {
        return postMapper.selectCount(null);
    }

    public Long countPublished() {
        return postMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getStatus, 1));
    }

    public List<Map<String, Object>> dailyCreatedCount(LocalDateTime start, LocalDateTime end) {
        return postMapper.dailyCreatedCount(start, end);
    }

    public void increaseCommentCount(Long postId, int delta) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return;
        }
        int count = post.getCommentCount() == null ? 0 : post.getCommentCount();
        count = Math.max(0, count + delta);
        Post update = new Post();
        update.setId(postId);
        update.setCommentCount(count);
        postMapper.updateById(update);
    }

    private void replacePostTags(Long postId, List<Long> tagIds) {
        postTagMapper.delete(new LambdaQueryWrapper<PostTag>().eq(PostTag::getPostId, postId));
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        LinkedHashSet<Long> set = new LinkedHashSet<>();
        for (Long tagId : tagIds) {
            if (tagId == null) {
                continue;
            }
            if (!tagService.existsEnabled(tagId)) {
                throw new BusinessException("标签不存在或已停用");
            }
            set.add(tagId);
        }
        for (Long tagId : set) {
            PostTag pt = new PostTag();
            pt.setPostId(postId);
            pt.setTagId(tagId);
            postTagMapper.insert(pt);
        }
    }

    private List<PostVO> buildPostVOList(List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> categoryIds = new HashSet<>();
        Set<Long> authorIds = new HashSet<>();
        Set<Long> postIds = new HashSet<>();
        for (Post post : posts) {
            if (post.getCategoryId() != null) {
                categoryIds.add(post.getCategoryId());
            }
            if (post.getAuthorId() != null) {
                authorIds.add(post.getAuthorId());
            }
            postIds.add(post.getId());
        }
        Map<Long, String> categoryNameMap = categoryService.nameMap(new ArrayList<>(categoryIds));
        Map<Long, String> userNameMap = new HashMap<>();
        if (!authorIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(authorIds);
            for (User user : users) {
                userNameMap.put(user.getId(), StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername());
            }
        }

        Map<Long, List<Long>> postTagIdsMap = new HashMap<>();
        Set<Long> tagIds = new HashSet<>();
        List<PostTag> postTags = postTagMapper.selectList(new LambdaQueryWrapper<PostTag>().in(PostTag::getPostId, postIds));
        for (PostTag postTag : postTags) {
            postTagIdsMap.computeIfAbsent(postTag.getPostId(), k -> new ArrayList<>()).add(postTag.getTagId());
            tagIds.add(postTag.getTagId());
        }
        Map<Long, String> tagNameMap = tagService.nameMap(new ArrayList<>(tagIds));

        List<PostVO> result = new ArrayList<>();
        for (Post post : posts) {
            PostVO vo = new PostVO();
            BeanUtils.copyProperties(post, vo);
            vo.setCategoryName(categoryNameMap.getOrDefault(post.getCategoryId(), ""));
            vo.setAuthorName(userNameMap.getOrDefault(post.getAuthorId(), ""));
            List<Long> ids = postTagIdsMap.getOrDefault(post.getId(), new ArrayList<>());
            vo.setTagIds(ids);
            vo.setTagNames(ids.stream().map(tagNameMap::get).filter(StringUtils::hasText).collect(Collectors.toList()));
            result.add(vo);
        }
        return result;
    }

    private String buildSummary(String summary, String content) {
        if (StringUtils.hasText(summary)) {
            String s = summary.trim();
            return s.length() > 300 ? s.substring(0, 300) : s;
        }
        String c = content == null ? "" : content.replaceAll("\\s+", " ").trim();
        if (c.length() <= 120) {
            return c;
        }
        return c.substring(0, 120);
    }

    private Integer normalizePostStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 0;
        }
        if (s != 0 && s != 1) {
            throw new BusinessException("文章状态不合法");
        }
        return s;
    }

    private Integer normalizeTop(Integer isTop, Integer fallback) {
        Integer t = isTop == null ? fallback : isTop;
        if (t == null) {
            t = 0;
        }
        if (t != 0 && t != 1) {
            throw new BusinessException("置顶状态不合法");
        }
        return t;
    }
}
