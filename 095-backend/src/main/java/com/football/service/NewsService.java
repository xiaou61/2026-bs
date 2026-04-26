package com.football.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.NewsNotice;
import com.football.entity.User;
import com.football.mapper.NewsNoticeMapper;
import com.football.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Resource
    private NewsNoticeMapper newsNoticeMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<NewsNotice> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<NewsNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<NewsNotice> wrapper = new LambdaQueryWrapper<NewsNotice>()
                .like(StringUtils.hasText(title), NewsNotice::getTitle, title == null ? null : title.trim())
                .eq(status != null, NewsNotice::getStatus, status)
                .orderByDesc(NewsNotice::getPublishTime);
        Page<NewsNotice> resultPage = newsNoticeMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<NewsNotice> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<NewsNotice> publicList() {
        List<NewsNotice> list = newsNoticeMapper.selectList(new LambdaQueryWrapper<NewsNotice>()
                .eq(NewsNotice::getStatus, 1)
                .orderByDesc(NewsNotice::getPublishTime)
                .last("limit 8"));
        fillDetails(list);
        return list;
    }

    public void save(NewsNotice notice, Long authorId) {
        if (notice == null || !StringUtils.hasText(notice.getTitle()) || !StringUtils.hasText(notice.getContent())) {
            throw new BusinessException("资讯内容不完整");
        }
        if (notice.getId() == null) {
            notice.setAuthorId(authorId);
            notice.setStatus(notice.getStatus() == null ? 1 : notice.getStatus());
            notice.setPublishTime(notice.getPublishTime() == null ? LocalDateTime.now() : notice.getPublishTime());
            newsNoticeMapper.insert(notice);
            return;
        }
        NewsNotice db = newsNoticeMapper.selectById(notice.getId());
        if (db == null) {
            throw new BusinessException("资讯不存在");
        }
        db.setTitle(notice.getTitle().trim());
        db.setContent(notice.getContent().trim());
        db.setNoticeType(StringUtils.hasText(notice.getNoticeType()) ? notice.getNoticeType().trim() : null);
        db.setPublishTime(notice.getPublishTime() == null ? db.getPublishTime() : notice.getPublishTime());
        if (notice.getStatus() != null) {
            db.setStatus(notice.getStatus());
        }
        newsNoticeMapper.updateById(db);
    }

    public void deleteById(Long id) {
        newsNoticeMapper.deleteById(id);
    }

    private void fillDetails(List<NewsNotice> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(NewsNotice::getAuthorId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setAuthorName(userMap.get(item.getAuthorId())));
    }
}
