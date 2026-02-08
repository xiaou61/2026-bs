package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.MovieCategory;
import com.movie.mapper.MovieCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieCategoryService {

    @Resource
    private MovieCategoryMapper movieCategoryMapper;

    public PageInfo<MovieCategory> getPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(movieCategoryMapper.selectPage(name));
    }

    public List<MovieCategory> getAll() {
        return movieCategoryMapper.selectAll();
    }

    public void add(MovieCategory category) {
        movieCategoryMapper.insert(category);
    }

    public void update(MovieCategory category) {
        movieCategoryMapper.update(category);
    }

    public void delete(Long id) {
        movieCategoryMapper.deleteById(id);
    }
}
