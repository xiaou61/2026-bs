package com.movie.mapper;

import com.movie.entity.Showtime;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ShowtimeMapper {
    List<Showtime> selectPage(@Param("movieId") Long movieId, @Param("cinemaId") Long cinemaId, @Param("showDate") String showDate);
    List<Showtime> selectByMovieId(Long movieId);
    Showtime selectById(Long id);
    int insert(Showtime showtime);
    int update(Showtime showtime);
    int updateAvailableSeats(@Param("id") Long id, @Param("count") Integer count);
    int deleteById(Long id);
}
