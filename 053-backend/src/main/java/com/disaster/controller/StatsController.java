package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DisasterMapper disasterMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private RescueTaskMapper taskMapper;

    @Autowired
    private DispatchMapper dispatchMapper;

    @GetMapping("/overview")
    public Result<?> overview() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.selectCount(null));
        result.put("disasterCount", disasterMapper.selectCount(null));
        result.put("warehouseCount", warehouseMapper.selectCount(null));
        result.put("materialCount", materialMapper.selectCount(null));
        result.put("taskCount", taskMapper.selectCount(null));
        result.put("dispatchCount", dispatchMapper.selectCount(null));
        return Result.success(result);
    }
}
