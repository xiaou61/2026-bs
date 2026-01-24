package com.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.common.BusinessException;
import com.security.entity.*;
import com.security.mapper.*;
import com.security.service.ArticleService;
import com.security.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, KnowledgeArticle> implements ArticleService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private LearnRecordMapper learnRecordMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<KnowledgeCategory> getCategoryList() {
        return categoryMapper.selectList(
                new LambdaQueryWrapper<KnowledgeCategory>()
                        .eq(KnowledgeCategory::getStatus, 1)
                        .orderByAsc(KnowledgeCategory::getSort));
    }

    @Override
    public Page<ArticleVO> getArticleList(Long categoryId, Integer page, Integer size) {
        Page<KnowledgeArticle> articlePage = new Page<>(page, size);
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeArticle::getStatus, 1);
        if (categoryId != null) {
            wrapper.eq(KnowledgeArticle::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(KnowledgeArticle::getCreateTime);
        this.page(articlePage, wrapper);

        Page<ArticleVO> voPage = new Page<>(page, size, articlePage.getTotal());
        List<ArticleVO> voList = articlePage.getRecords().stream().map(article -> {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            KnowledgeCategory category = categoryMapper.selectById(article.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
            return vo;
        }).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public ArticleVO getArticleDetail(Long id, Long userId) {
        KnowledgeArticle article = this.getById(id);
        if (article == null) {
            throw new BusinessException("文章不存在");
        }

        article.setViewCount(article.getViewCount() + 1);
        this.updateById(article);

        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);

        KnowledgeCategory category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }

        if (userId != null) {
            Long favoriteCount = favoriteMapper.selectCount(
                    new LambdaQueryWrapper<Favorite>()
                            .eq(Favorite::getUserId, userId)
                            .eq(Favorite::getArticleId, id));
            vo.setIsFavorite(favoriteCount > 0);

            String likeKey = "article:like:" + id + ":" + userId;
            vo.setIsLiked(redisTemplate.hasKey(likeKey));
        }

        return vo;
    }

    @Override
    public void likeArticle(Long id, Long userId) {
        String likeKey = "article:like:" + id + ":" + userId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(likeKey))) {
            throw new BusinessException("已经点赞过了");
        }
        redisTemplate.opsForValue().set(likeKey, "1", 365, TimeUnit.DAYS);

        KnowledgeArticle article = this.getById(id);
        article.setLikeCount(article.getLikeCount() + 1);
        this.updateById(article);
    }

    @Override
    public void addFavorite(Long articleId, Long userId) {
        Long count = favoriteMapper.selectCount(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getArticleId, articleId));
        if (count > 0) {
            throw new BusinessException("已收藏");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setArticleId(articleId);
        favoriteMapper.insert(favorite);
    }

    @Override
    public void removeFavorite(Long articleId, Long userId) {
        favoriteMapper.delete(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getArticleId, articleId));
    }

    @Override
    public Page<ArticleVO> getFavoriteList(Long userId, Integer page, Integer size) {
        Page<Favorite> favoritePage = new Page<>(page, size);
        favoriteMapper.selectPage(favoritePage,
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .orderByDesc(Favorite::getCreateTime));

        Page<ArticleVO> voPage = new Page<>(page, size, favoritePage.getTotal());
        List<ArticleVO> voList = favoritePage.getRecords().stream().map(favorite -> {
            KnowledgeArticle article = this.getById(favorite.getArticleId());
            ArticleVO vo = new ArticleVO();
            if (article != null) {
                BeanUtils.copyProperties(article, vo);
                KnowledgeCategory category = categoryMapper.selectById(article.getCategoryId());
                if (category != null) {
                    vo.setCategoryName(category.getName());
                }
            }
            vo.setIsFavorite(true);
            return vo;
        }).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public void recordLearn(Long articleId, Long userId, Integer progress) {
        LearnRecord record = learnRecordMapper.selectOne(
                new LambdaQueryWrapper<LearnRecord>()
                        .eq(LearnRecord::getUserId, userId)
                        .eq(LearnRecord::getArticleId, articleId));
        if (record == null) {
            record = new LearnRecord();
            record.setUserId(userId);
            record.setArticleId(articleId);
            record.setProgress(progress);
            learnRecordMapper.insert(record);
        } else {
            if (progress > record.getProgress()) {
                record.setProgress(progress);
                learnRecordMapper.updateById(record);
            }
        }
    }
}
