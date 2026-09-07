package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.CreditLog;
import com.xiaou.campusshare.mapper.CreditLogMapper;
import org.springframework.stereotype.Service;

@Service
public class CreditService extends ServiceImpl<CreditLogMapper, CreditLog> {

    public void addCreditLog(Long userId, Integer scoreChange, Integer beforeScore, Integer afterScore, String reason, Long orderId) {
        CreditLog log = new CreditLog();
        log.setUserId(userId);
        log.setChangeScore(scoreChange);
        log.setBeforeScore(beforeScore);
        log.setAfterScore(afterScore);
        log.setReason(reason);
        log.setRelatedOrderId(orderId);
        
        String changeType = "";
        if (scoreChange > 0) {
            if (reason != null && reason.contains("实名认证")) {
                changeType = "AUTH";
            } else if (reason != null && reason.contains("按时归还")) {
                changeType = "ON_TIME";
            } else if (reason != null && reason.contains("好评")) {
                changeType = "GOOD_REVIEW";
            }
        } else {
            if (reason != null && reason.contains("超时")) {
                changeType = "OVERTIME";
            } else if (reason != null && reason.contains("损坏")) {
                changeType = "DAMAGE";
            } else if (reason != null && reason.contains("违约")) {
                changeType = "VIOLATION";
            }
        }
        log.setChangeType(changeType);
        
        save(log);
    }
}

