package com.bike.service;

import com.bike.entity.Station;
import com.bike.mapper.StationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationMapper stationMapper;

    public PageInfo<Station> getList(Integer pageNum, Integer pageSize, String name, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(stationMapper.findList(name, status));
    }

    public List<Station> getAll() {
        return stationMapper.findAll();
    }

    public Station getById(Long id) {
        return stationMapper.findById(id);
    }

    public void add(Station station) {
        if (station.getCurrentCount() == null) {
            station.setCurrentCount(0);
        }
        if (station.getStatus() == null) {
            station.setStatus(1);
        }
        stationMapper.insert(station);
    }

    public void update(Station station) {
        stationMapper.update(station);
    }

    public void delete(Long id) {
        stationMapper.deleteById(id);
    }
}
