package com.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.common.BusinessException;
import com.security.entity.News;
import com.security.mapper.NewsMapper;
import com.security.service.NewsService;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public Page<News> getNewsList(Integer page, Integer size) {
        Page<News> newsPage = new Page<>(page, size);
        return this.page(newsPage,
                new LambdaQueryWrapper<News>()
                        .eq(News::getStatus, 1)
                        .orderByDesc(News::getPublishTime));
    }

    @Override
    public News getNewsDetail(Long id) {
        News news = this.getById(id);
        if (news == null) {
            throw new BusinessException("资讯不存在");
        }
        news.setViewCount(news.getViewCount() + 1);
        this.updateById(news);
        return news;
    }
}
