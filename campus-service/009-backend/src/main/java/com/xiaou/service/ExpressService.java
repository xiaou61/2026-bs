package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Express;
import com.xiaou.mapper.ExpressMapper;
import org.springframework.stereotype.Service;

@Service
public class ExpressService extends ServiceImpl<ExpressMapper, Express> {
}

