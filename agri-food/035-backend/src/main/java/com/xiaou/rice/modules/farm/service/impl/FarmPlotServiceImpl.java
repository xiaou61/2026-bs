package com.xiaou.rice.modules.farm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.rice.modules.farm.entity.FarmPlot;
import com.xiaou.rice.modules.farm.mapper.FarmPlotMapper;
import com.xiaou.rice.modules.farm.service.FarmPlotService;
import org.springframework.stereotype.Service;

@Service
public class FarmPlotServiceImpl extends ServiceImpl<FarmPlotMapper, FarmPlot> implements FarmPlotService {
}
