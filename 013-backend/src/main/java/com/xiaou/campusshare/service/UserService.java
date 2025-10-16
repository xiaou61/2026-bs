package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private CreditService creditService;

    public User getUserById(Long userId) {
        return getById(userId);
    }

    public boolean updateDepositBalance(Long userId, BigDecimal amount) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        user.setDepositBalance(user.getDepositBalance().add(amount));
        return updateById(user);
    }

    public boolean updateAccountBalance(Long userId, BigDecimal amount) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        user.setAccountBalance(user.getAccountBalance().add(amount));
        return updateById(user);
    }

    public boolean updateCreditScore(Long userId, Integer scoreChange, String reason, Long orderId) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        int newScore = user.getCreditScore() + scoreChange;
        if (newScore < 0) newScore = 0;
        if (newScore > 150) newScore = 150;
        
        creditService.addCreditLog(userId, scoreChange, user.getCreditScore(), newScore, reason, orderId);
        user.setCreditScore(newScore);
        return updateById(user);
    }
}

