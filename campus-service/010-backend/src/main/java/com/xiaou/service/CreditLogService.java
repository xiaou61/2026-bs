package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.CreditLog;
import com.xiaou.mapper.CreditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreditLogService {

    @Autowired
    private CreditLogMapper creditLogMapper;

    public List<CreditLog> listMyCreditLogs(Long userId) {
        LambdaQueryWrapper<CreditLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CreditLog::getUserId, userId);
        wrapper.orderByDesc(CreditLog::getCreateTime);
        return creditLogMapper.selectList(wrapper);
    }
}

