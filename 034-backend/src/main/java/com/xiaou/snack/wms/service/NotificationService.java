package com.xiaou.snack.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.snack.wms.entity.order.NotificationRecord;

public interface NotificationService extends IService<NotificationRecord> {
    void notifyRecord(NotificationRecord record);
}
