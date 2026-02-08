package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Movie;
import com.movie.mapper.MovieMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieService {

    @Resource
    private MovieMapper movieMapper;

    public PageInfo<Movie> getPage(Integer pageNum, Integer pageSize, String title, Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(movieMapper.selectPage(title, categoryId));
    }

    public Movie getById(Long id) {
        return movieMapper.selectById(id);
    }

    public List<Movie> getHot() {
        return movieMapper.selectHot();
    }

    public void add(Movie movie) {
        movieMapper.insert(movie);
    }

    public void update(Movie movie) {
        movieMapper.update(movie);
    }

    public void delete(Long id) {
        movieMapper.deleteById(id);
    }

    public void updateScore(Long movieId, Double score) {
        movieMapper.updateScore(movieId, score);
    }
}
