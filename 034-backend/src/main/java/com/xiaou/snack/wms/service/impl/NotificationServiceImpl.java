package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.entity.order.NotificationRecord;
import com.xiaou.snack.wms.mapper.NotificationRecordMapper;
import com.xiaou.snack.wms.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationRecordMapper, NotificationRecord> implements NotificationService {
    @Override
    public void notifyRecord(NotificationRecord record) {
        save(record);
    }
}
