package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.entity.ExchangeRecord;
import com.xiaou.entity.PointsProduct;
import com.xiaou.entity.PointsRecord;

public interface PointsService {
    IPage<PointsRecord> getPointsRecords(Long userId, Integer current, Integer size);
    IPage<PointsProduct> getProducts(Integer current, Integer size);
    void exchange(Long userId, Long productId, Integer quantity);
    IPage<ExchangeRecord> getExchangeRecords(Long userId, Integer current, Integer size);
}
