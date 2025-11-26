package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Fee;
import com.xiaou.community.mapper.FeeMapper;
import com.xiaou.community.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class FeeServiceImpl implements FeeService {
    @Autowired
    private FeeMapper feeMapper;

    @Override
    public void create(Fee fee) {
        fee.setStatus("UNPAID");
        feeMapper.insert(fee);
    }

    @Override
    public void pay(Integer id) {
        Fee fee = new Fee();
        fee.setId(id);
        fee.setStatus("PAID");
        fee.setPayTime(new Date());
        feeMapper.update(fee);
    }

    @Override
    public List<Fee> getByOwnerId(Integer ownerId) {
        return feeMapper.findByOwnerId(ownerId);
    }

    @Override
    public List<Fee> getAll() {
        return feeMapper.findAll();
    }
}
