package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Bed;
import com.xiaou.mapper.BedMapper;
import com.xiaou.service.BedService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements BedService {

    @Override
    public List<Bed> getAvailableBeds() {
        return list(new LambdaQueryWrapper<Bed>().eq(Bed::getStatus, 0));
    }

    @Override
    public List<Map<String, Object>> getBedStatistics() {
        List<Bed> all = list();
        long total = all.size();
        long occupied = all.stream().filter(b -> b.getStatus() == 1).count();
        long available = all.stream().filter(b -> b.getStatus() == 0).count();
        long reserved = all.stream().filter(b -> b.getStatus() == 2).count();

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("occupied", occupied);
        stats.put("available", available);
        stats.put("reserved", reserved);
        stats.put("occupancyRate", total > 0 ? (double) occupied / total * 100 : 0);
        result.add(stats);
        return result;
    }
}
