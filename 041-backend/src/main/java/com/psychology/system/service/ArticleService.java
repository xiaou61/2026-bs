package com.psychology.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psychology.system.entity.Article;
import com.psychology.system.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;

    public List<Article> getArticles(String category) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "PUBLISHED");
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        wrapper.orderByDesc("is_top", "is_recommended", "created_at");
        return articleMapper.selectList(wrapper);
    }

    public Article getArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            articleMapper.updateById(article);
        }
        return article;
    }
}
