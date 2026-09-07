package com.movie.mapper;

import com.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface MovieMapper {
    List<Movie> selectPage(@Param("title") String title, @Param("categoryId") Long categoryId);
    Movie selectById(Long id);
    List<Movie> selectHot();
    int insert(Movie movie);
    int update(Movie movie);
    int deleteById(Long id);
    int count();
    int updateScore(@Param("id") Long id, @Param("score") Double score);
    List<Map<String, Object>> selectCategoryStats();
}
