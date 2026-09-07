package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.OverdueRecord;
import com.xiaou.mapper.OverdueRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class OverdueRecordService extends ServiceImpl<OverdueRecordMapper, OverdueRecord> {
}

