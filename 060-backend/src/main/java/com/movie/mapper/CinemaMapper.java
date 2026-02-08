package com.movie.mapper;

import com.movie.entity.Cinema;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CinemaMapper {
    List<Cinema> selectPage(@Param("name") String name);
    List<Cinema> selectAll();
    Cinema selectById(Long id);
    int insert(Cinema cinema);
    int update(Cinema cinema);
    int deleteById(Long id);
}
