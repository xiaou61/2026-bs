package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Hall;
import com.movie.mapper.HallMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HallService {

    @Resource
    private HallMapper hallMapper;

    public PageInfo<Hall> getPage(Integer pageNum, Integer pageSize, Long cinemaId, String name) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(hallMapper.selectPage(cinemaId, name));
    }

    public List<Hall> getByCinemaId(Long cinemaId) {
        return hallMapper.selectByCinemaId(cinemaId);
    }

    public void add(Hall hall) {
        hallMapper.insert(hall);
    }

    public void update(Hall hall) {
        hallMapper.update(hall);
    }

    public void delete(Long id) {
        hallMapper.deleteById(id);
    }
}
