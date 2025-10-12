package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Station;
import com.xiaou.mapper.StationMapper;
import org.springframework.stereotype.Service;

@Service
public class StationService extends ServiceImpl<StationMapper, Station> {
}

