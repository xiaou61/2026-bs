package com.movie.mapper;

import com.movie.entity.MovieCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MovieCategoryMapper {
    List<MovieCategory> selectPage(@Param("name") String name);
    List<MovieCategory> selectAll();
    MovieCategory selectById(Long id);
    int insert(MovieCategory category);
    int update(MovieCategory category);
    int deleteById(Long id);
}
