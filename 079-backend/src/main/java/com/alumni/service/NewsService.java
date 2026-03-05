package com.alumni.service;

import com.alumni.entity.News;
import com.alumni.entity.NewsComment;
import com.alumni.entity.User;
import com.alumni.mapper.NewsCommentMapper;
import com.alumni.mapper.NewsMapper;
import com.alumni.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsCommentMapper newsCommentMapper;

    @Autowired
    private UserMapper userMapper;

    public Page<News> list(Integer pageNum, Integer pageSize, String category, String title) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(category)) {
            wrapper.eq(News::getCategory, category);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like(News::getTitle, title);
        }
        wrapper.eq(News::getStatus, 1);
        wrapper.orderByDesc(News::getIsTop).orderByDesc(News::getCreateTime);
        Page<News> result = newsMapper.selectPage(page, wrapper);
        List<User> users = userMapper.selectList(null);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        result.getRecords().forEach(n -> n.setAuthorName(userMap.get(n.getAuthorId())));
        return result;
    }

    public News getById(Long id) {
        News news = newsMapper.selectById(id);
        if (news != null) {
            news.setViewCount(news.getViewCount() + 1);
            newsMapper.updateById(news);
            User author = userMapper.selectById(news.getAuthorId());
            if (author != null) news.setAuthorName(author.getName());
        }
        return news;
    }

    public void add(News news) {
        news.setViewCount(0);
        news.setStatus(1);
        newsMapper.insert(news);
    }

    public void update(News news) {
        newsMapper.updateById(news);
    }

    public void delete(Long id) {
        newsMapper.deleteById(id);
        newsCommentMapper.delete(new LambdaQueryWrapper<NewsComment>().eq(NewsComment::getNewsId, id));
    }

    public List<NewsComment> getComments(Long newsId) {
        List<NewsComment> comments = newsCommentMapper.selectList(
                new LambdaQueryWrapper<NewsComment>().eq(NewsComment::getNewsId, newsId).orderByDesc(NewsComment::getCreateTime));
        List<User> users = userMapper.selectList(null);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        comments.forEach(c -> {
            User user = userMap.get(c.getUserId());
            if (user != null) {
                c.setUserName(user.getName());
                c.setUserAvatar(user.getAvatar());
            }
        });
        return comments;
    }

    public void addComment(NewsComment comment) {
        newsCommentMapper.insert(comment);
    }

    public void deleteComment(Long id) {
        newsCommentMapper.deleteById(id);
    }
}
