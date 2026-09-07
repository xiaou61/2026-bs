package com.bike.service;

import com.bike.mapper.BikeMapper;
import com.bike.mapper.RideOrderMapper;
import com.bike.mapper.StationMapper;
import com.bike.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BikeMapper bikeMapper;
    @Autowired
    private RideOrderMapper rideOrderMapper;
    @Autowired
    private StationMapper stationMapper;

    public Map<String, Object> getOverview() {
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userMapper.countTotal());
        data.put("bikeCount", bikeMapper.countTotal());
        data.put("todayOrders", rideOrderMapper.countToday());
        BigDecimal income = rideOrderMapper.sumTodayIncome();
        data.put("todayIncome", income != null ? income : BigDecimal.ZERO);
        return data;
    }

    public List<Map<String, Object>> getRideTrend(Integer days) {
        return rideOrderMapper.rideTrend(days);
    }

    public List<Map<String, Object>> getIncomeTrend(Integer days) {
        return rideOrderMapper.incomeTrend(days);
    }

    public List<Map<String, Object>> getStationRank() {
        return stationMapper.stationRank();
    }

    public List<Map<String, Object>> getBikeTypeRatio() {
        return bikeMapper.countByType();
    }
}
