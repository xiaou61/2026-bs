package com.programming.learning.mapper;

import com.programming.learning.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper接口
 */
@Mapper
public interface ArticleMapper {

    int insert(Article article);

    int deleteById(Long id);

    int update(Article article);

    Article selectById(Long id);

    List<Article> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    Long countAll();

    List<Article> selectByUserId(@Param("userId") Long userId);

    List<Article> selectByColumnId(@Param("columnId") Long columnId);

    List<Article> selectByTag(@Param("tag") String tag, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Article> selectHot(@Param("limit") Integer limit);

    int incrementViewCount(Long id);

    int incrementLikeCount(Long id);

    int decrementLikeCount(Long id);

    int incrementFavoriteCount(Long id);

    int decrementFavoriteCount(Long id);

    int incrementCommentCount(Long id);

    int decrementCommentCount(Long id);

    List<Article> search(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
