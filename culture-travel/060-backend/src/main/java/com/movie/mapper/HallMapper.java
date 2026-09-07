package com.movie.mapper;

import com.movie.entity.Hall;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface HallMapper {
    List<Hall> selectPage(@Param("cinemaId") Long cinemaId, @Param("name") String name);
    List<Hall> selectByCinemaId(Long cinemaId);
    Hall selectById(Long id);
    int insert(Hall hall);
    int update(Hall hall);
    int deleteById(Long id);
}
