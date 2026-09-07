package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Review;
import com.movie.mapper.MovieMapper;
import com.movie.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private MovieMapper movieMapper;

    public List<Review> getByMovieId(Long movieId) {
        return reviewMapper.selectByMovieId(movieId);
    }

    public PageInfo<Review> getPage(Integer pageNum, Integer pageSize, String movieTitle) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(reviewMapper.selectPage(movieTitle));
    }

    public void add(Review review) {
        review.setStatus(1);
        reviewMapper.insert(review);
        Double avg = reviewMapper.selectAvgRatingByMovieId(review.getMovieId());
        if (avg != null) {
            movieMapper.updateScore(review.getMovieId(), avg);
        }
    }

    public void delete(Long id) {
        reviewMapper.deleteById(id);
    }
}
