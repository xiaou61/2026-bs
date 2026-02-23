package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Movie;
import com.ticket.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    public Page<Movie> listMovies(Integer pageNum, Integer pageSize, String title, String status, String category) {
        Page<Movie> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like("title", title);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        wrapper.orderByDesc("create_time");
        return movieMapper.selectPage(page, wrapper);
    }

    public Movie getMovieById(Long id) {
        Movie movie = movieMapper.selectById(id);
        if (movie == null) {
            throw new BusinessException("电影不存在");
        }
        return movie;
    }

    public void addMovie(Movie movie) {
        movieMapper.insert(movie);
    }

    public void updateMovie(Movie movie) {
        movieMapper.updateById(movie);
    }

    public void deleteMovie(Long id) {
        movieMapper.deleteById(id);
    }

    public List<Movie> getRecommendMovies() {
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("is_recommend", 1)
                .eq("status", "showing")
                .orderByDesc("rating")
                .last("LIMIT 10");
        return movieMapper.selectList(wrapper);
    }

    public void updateRating(Long movieId, Integer rating) {
        Movie movie = movieMapper.selectById(movieId);
        if (movie == null) {
            return;
        }
        Integer count = movie.getCommentCount() + 1;
        movie.setCommentCount(count);
        movieMapper.updateById(movie);
    }
}
