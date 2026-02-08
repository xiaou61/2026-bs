package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Cinema;
import com.movie.mapper.CinemaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CinemaService {

    @Resource
    private CinemaMapper cinemaMapper;

    public PageInfo<Cinema> getPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(cinemaMapper.selectPage(name));
    }

    public List<Cinema> getAll() {
        return cinemaMapper.selectAll();
    }

    public void add(Cinema cinema) {
        cinemaMapper.insert(cinema);
    }

    public void update(Cinema cinema) {
        cinemaMapper.update(cinema);
    }

    public void delete(Long id) {
        cinemaMapper.deleteById(id);
    }
}
