package com.movie.mapper;

import com.movie.entity.Review;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ReviewMapper {
    List<Review> selectByMovieId(Long movieId);
    List<Review> selectPage(@Param("movieTitle") String movieTitle);
    int insert(Review review);
    int deleteById(Long id);
    Double selectAvgRatingByMovieId(Long movieId);
}
