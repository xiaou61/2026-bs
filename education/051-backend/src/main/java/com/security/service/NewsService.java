package com.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.security.entity.News;

public interface NewsService extends IService<News> {
    Page<News> getNewsList(Integer page, Integer size);
    News getNewsDetail(Long id);
}
