package com.xiaou.rice.modules.machine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.rice.modules.machine.entity.Machine;
import com.xiaou.rice.modules.machine.mapper.MachineMapper;
import com.xiaou.rice.modules.machine.service.MachineService;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements MachineService {
}