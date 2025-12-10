package com.xiaou.bike.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.entity.Station;
import com.xiaou.bike.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 停车点服务
 */
@Service
@RequiredArgsConstructor
public class StationService {

    private final StationMapper stationMapper;

    /**
     * 获取所有停车点
     */
    public List<Station> listAll() {
        LambdaQueryWrapper<Station> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Station::getStatus, 1);
        wrapper.orderByAsc(Station::getId);
        return stationMapper.selectList(wrapper);
    }

    /**
     * 获取停车点详情
     */
    public Station getById(Long id) {
        return stationMapper.selectById(id);
    }

    /**
     * 分页查询停车点（管理端）
     */
    public Page<Station> listStations(Integer page, Integer size, String keyword, Integer status) {
        Page<Station> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Station> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Station::getStationName, keyword)
                    .or().like(Station::getAddress, keyword);
        }
        
        if (status != null) {
            wrapper.eq(Station::getStatus, status);
        }
        
        wrapper.orderByDesc(Station::getCreateTime);
        return stationMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 新增停车点
     */
    public void addStation(Station station) {
        station.setCurrentCount(0);
        stationMapper.insert(station);
    }

    /**
     * 更新停车点
     */
    public void updateStation(Station station) {
        stationMapper.updateById(station);
    }

    /**
     * 删除停车点
     */
    public void deleteStation(Long id) {
        stationMapper.deleteById(id);
    }

    /**
     * 更新停车点车辆数量
     */
    public void updateCount(Long stationId, int delta) {
        Station station = stationMapper.selectById(stationId);
        if (station != null) {
            int newCount = station.getCurrentCount() + delta;
            if (newCount < 0) newCount = 0;
            if (newCount > station.getCapacity()) newCount = station.getCapacity();
            
            Station update = new Station();
            update.setId(stationId);
            update.setCurrentCount(newCount);
            stationMapper.updateById(update);
        }
    }
}
