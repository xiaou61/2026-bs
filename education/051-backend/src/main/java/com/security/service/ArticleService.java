package com.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.security.entity.KnowledgeArticle;
import com.security.entity.KnowledgeCategory;
import com.security.vo.ArticleVO;

import java.util.List;

public interface ArticleService extends IService<KnowledgeArticle> {
    List<KnowledgeCategory> getCategoryList();
    Page<ArticleVO> getArticleList(Long categoryId, Integer page, Integer size);
    ArticleVO getArticleDetail(Long id, Long userId);
    void likeArticle(Long id, Long userId);
    void addFavorite(Long articleId, Long userId);
    void removeFavorite(Long articleId, Long userId);
    Page<ArticleVO> getFavoriteList(Long userId, Integer page, Integer size);
    void recordLearn(Long articleId, Long userId, Integer progress);
}
