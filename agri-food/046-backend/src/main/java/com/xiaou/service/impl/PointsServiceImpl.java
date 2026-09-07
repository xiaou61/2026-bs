package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.PointsService;
import com.xiaou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

    private final PointsRecordMapper pointsRecordMapper;
    private final PointsProductMapper productMapper;
    private final ExchangeRecordMapper exchangeRecordMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public IPage<PointsRecord> getPointsRecords(Long userId, Integer current, Integer size) {
        return pointsRecordMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<PointsRecord>()
                        .eq(PointsRecord::getUserId, userId)
                        .orderByDesc(PointsRecord::getCreateTime));
    }

    @Override
    public IPage<PointsProduct> getProducts(Integer current, Integer size) {
        return productMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<PointsProduct>()
                        .eq(PointsProduct::getStatus, 1)
                        .orderByAsc(PointsProduct::getPoints));
    }

    @Override
    @Transactional
    public void exchange(Long userId, Long productId, Integer quantity) {
        User user = userMapper.selectById(userId);
        PointsProduct product = productMapper.selectById(productId);

        if (product == null || product.getStatus() != 1) {
            throw new BusinessException("商品不存在或已下架");
        }
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        int totalPoints = product.getPoints() * quantity;
        if (user.getPoints() < totalPoints) {
            throw new BusinessException("积分不足");
        }

        // 扣减积分
        userService.updatePoints(userId, -totalPoints);

        // 更新库存
        product.setStock(product.getStock() - quantity);
        product.setExchangeCount(product.getExchangeCount() + quantity);
        productMapper.updateById(product);

        // 创建兑换记录
        ExchangeRecord record = new ExchangeRecord();
        record.setUserId(userId);
        record.setProductId(productId);
        record.setProductName(product.getName());
        record.setPoints(totalPoints);
        record.setQuantity(quantity);
        record.setStatus(0);
        exchangeRecordMapper.insert(record);

        // 积分记录
        User updatedUser = userMapper.selectById(userId);
        PointsRecord pointsRecord = new PointsRecord();
        pointsRecord.setUserId(userId);
        pointsRecord.setPoints(-totalPoints);
        pointsRecord.setType(2);
        pointsRecord.setDescription("兑换商品:" + product.getName());
        pointsRecord.setBalance(updatedUser.getPoints());
        pointsRecordMapper.insert(pointsRecord);
    }

    @Override
    public IPage<ExchangeRecord> getExchangeRecords(Long userId, Integer current, Integer size) {
        return exchangeRecordMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<ExchangeRecord>()
                        .eq(ExchangeRecord::getUserId, userId)
                        .orderByDesc(ExchangeRecord::getCreateTime));
    }
}
