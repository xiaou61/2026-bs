package com.parkingguide.service;

import com.parkingguide.mapper.ParkingLotMapper;
import com.parkingguide.mapper.ParkingSpaceMapper;
import com.parkingguide.mapper.ReservationOrderMapper;
import com.parkingguide.mapper.VacancyPredictionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ParkingLotMapper parkingLotMapper;
    private final ParkingSpaceMapper parkingSpaceMapper;
    private final ReservationOrderMapper reservationOrderMapper;
    private final VacancyPredictionMapper vacancyPredictionMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("lotCount", parkingLotMapper.selectCount(null));
        data.put("spaceCount", parkingSpaceMapper.selectCount(null));
        data.put("reservationCount", reservationOrderMapper.selectCount(null));
        data.put("predictionCount", vacancyPredictionMapper.selectCount(null));
        data.put("parkingTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("spacePie", Arrays.asList(map("空闲", 36), map("占用", 28), map("预约", 22), map("维修", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
