package com.bike.service;

import com.bike.entity.CreditRecord;
import com.bike.entity.User;
import com.bike.mapper.CreditRecordMapper;
import com.bike.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditService {

    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private UserMapper userMapper;

    public Integer getScore(Long userId) {
        User user = userMapper.findById(userId);
        return user.getCreditScore();
    }

    public PageInfo<CreditRecord> getRecords(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(creditRecordMapper.findByUserId(userId));
    }

    @Transactional
    public void adjust(Long userId, Integer scoreChange, String description) {
        User user = userMapper.findById(userId);
        int newScore = user.getCreditScore() + scoreChange;
        if (newScore < 0) newScore = 0;
        if (newScore > 200) newScore = 200;
        userMapper.updateCreditScore(userId, newScore);
        CreditRecord record = new CreditRecord();
        record.setUserId(userId);
        record.setType(scoreChange > 0 ? 1 : 2);
        record.setScoreChange(scoreChange);
        record.setScoreAfter(newScore);
        record.setDescription(description);
        creditRecordMapper.insert(record);
    }
}
