package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Bed;

import java.util.List;
import java.util.Map;

public interface BedService extends IService<Bed> {
    List<Bed> getAvailableBeds();
    List<Map<String, Object>> getBedStatistics();
}
