package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.Station;
import com.railway.mapper.StationMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StationService {

    @Resource
    private StationMapper stationMapper;

    public PageResult<Station> page(Integer pageNum, Integer pageSize, String stationName, String city, Integer status) {
        Page<Station> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Station> wrapper = new LambdaQueryWrapper<Station>()
                .like(StringUtils.hasText(stationName), Station::getStationName, trim(stationName))
                .like(StringUtils.hasText(city), Station::getCity, trim(city))
                .eq(status != null, Station::getStatus, status)
                .orderByAsc(Station::getStationCode);
        Page<Station> resultPage = stationMapper.selectPage(page, wrapper);
        PageResult<Station> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Station> enabledList() {
        return stationMapper.selectList(new LambdaQueryWrapper<Station>()
                .eq(Station::getStatus, 1)
                .orderByAsc(Station::getStationCode));
    }

    public Station getById(Long id) {
        Station station = stationMapper.selectById(id);
        if (station == null) {
            throw new BusinessException("车站不存在");
        }
        return station;
    }

    public void save(Station station) {
        validate(station);
        if (station.getId() == null) {
            assertUnique(station, null);
            station.setStationCode(station.getStationCode().trim());
            station.setStationName(station.getStationName().trim());
            station.setCity(trim(station.getCity()));
            station.setProvince(trim(station.getProvince()));
            station.setAddress(trim(station.getAddress()));
            station.setStatus(station.getStatus() == null ? 1 : station.getStatus());
            stationMapper.insert(station);
            return;
        }
        Station db = getById(station.getId());
        assertUnique(station, db.getId());
        db.setStationCode(station.getStationCode().trim());
        db.setStationName(station.getStationName().trim());
        db.setCity(trim(station.getCity()));
        db.setProvince(trim(station.getProvince()));
        db.setAddress(trim(station.getAddress()));
        db.setStatus(station.getStatus() == null ? db.getStatus() : station.getStatus());
        stationMapper.updateById(db);
    }

    public void deleteById(Long id) {
        stationMapper.deleteById(id);
    }

    private void validate(Station station) {
        if (station == null || !StringUtils.hasText(station.getStationCode()) || !StringUtils.hasText(station.getStationName())) {
            throw new BusinessException("车站编码和名称不能为空");
        }
    }

    private void assertUnique(Station station, Long excludeId) {
        Station codeExist = stationMapper.selectOne(new LambdaQueryWrapper<Station>()
                .eq(Station::getStationCode, station.getStationCode().trim())
                .last("limit 1"));
        if (codeExist != null && (excludeId == null || !codeExist.getId().equals(excludeId))) {
            throw new BusinessException("车站编码已存在");
        }
        Station nameExist = stationMapper.selectOne(new LambdaQueryWrapper<Station>()
                .eq(Station::getStationName, station.getStationName().trim())
                .last("limit 1"));
        if (nameExist != null && (excludeId == null || !nameExist.getId().equals(excludeId))) {
            throw new BusinessException("车站名称已存在");
        }
    }

    private String trim(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
