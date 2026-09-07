package com.bike.service;

import com.bike.common.BusinessException;
import com.bike.entity.Bike;
import com.bike.mapper.BikeMapper;
import com.bike.mapper.StationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BikeService {

    @Autowired
    private BikeMapper bikeMapper;

    @Autowired
    private StationMapper stationMapper;

    public PageInfo<Bike> getList(Integer pageNum, Integer pageSize, String bikeNo, Integer type, Integer status, Long stationId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(bikeMapper.findList(bikeNo, type, status, stationId));
    }

    public Bike getById(Long id) {
        return bikeMapper.findById(id);
    }

    public List<Bike> getAvailableByStation(Long stationId) {
        return bikeMapper.findAvailableByStation(stationId);
    }

    @Transactional
    public void add(Bike bike) {
        bike.setStatus(1);
        bikeMapper.insert(bike);
        if (bike.getStationId() != null) {
            stationMapper.updateCurrentCount(bike.getStationId(), 1);
        }
    }

    public void update(Bike bike) {
        bikeMapper.update(bike);
    }

    @Transactional
    public void delete(Long id) {
        Bike bike = bikeMapper.findById(id);
        if (bike == null) {
            throw new BusinessException("单车不存在");
        }
        if (bike.getStatus() == 2) {
            throw new BusinessException("使用中的单车不能删除");
        }
        bikeMapper.deleteById(id);
        if (bike.getStationId() != null) {
            stationMapper.updateCurrentCount(bike.getStationId(), -1);
        }
    }

    @Transactional
    public void dispatch(Long bikeId, Long targetStationId) {
        Bike bike = bikeMapper.findById(bikeId);
        if (bike == null) {
            throw new BusinessException("单车不存在");
        }
        if (bike.getStatus() == 2) {
            throw new BusinessException("使用中的单车不能调度");
        }
        if (bike.getStationId() != null) {
            stationMapper.updateCurrentCount(bike.getStationId(), -1);
        }
        bikeMapper.updateStation(bikeId, targetStationId);
        stationMapper.updateCurrentCount(targetStationId, 1);
    }
}
