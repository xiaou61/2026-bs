package com.xiaou.bike.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.BusinessException;
import com.xiaou.bike.entity.Bicycle;
import com.xiaou.bike.mapper.BicycleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自行车服务
 */
@Service
@RequiredArgsConstructor
public class BicycleService {

    private final BicycleMapper bicycleMapper;

    /**
     * 根据车辆编号查询
     */
    public Bicycle getByBikeNo(String bikeNo) {
        LambdaQueryWrapper<Bicycle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bicycle::getBikeNo, bikeNo);
        return bicycleMapper.selectOne(wrapper);
    }

    /**
     * 根据ID查询
     */
    public Bicycle getById(Long id) {
        return bicycleMapper.selectById(id);
    }

    /**
     * 获取停车点可用车辆
     */
    public List<Bicycle> getAvailableBikes(Long stationId) {
        LambdaQueryWrapper<Bicycle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bicycle::getStationId, stationId);
        wrapper.eq(Bicycle::getStatus, 0); // 空闲状态
        return bicycleMapper.selectList(wrapper);
    }

    /**
     * 分页查询车辆（管理端）
     */
    public Page<Bicycle> listBicycles(Integer page, Integer size, String keyword, Integer status, Long stationId) {
        Page<Bicycle> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Bicycle> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Bicycle::getBikeNo, keyword);
        }
        
        if (status != null) {
            wrapper.eq(Bicycle::getStatus, status);
        }
        
        if (stationId != null) {
            wrapper.eq(Bicycle::getStationId, stationId);
        }
        
        wrapper.orderByDesc(Bicycle::getCreateTime);
        return bicycleMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 新增车辆
     */
    public void addBicycle(Bicycle bicycle) {
        // 检查车辆编号是否存在
        Bicycle exist = getByBikeNo(bicycle.getBikeNo());
        if (exist != null) {
            throw BusinessException.of("车辆编号已存在");
        }
        
        bicycle.setQrCode("QR_" + bicycle.getBikeNo());
        bicycle.setStatus(0);
        bicycleMapper.insert(bicycle);
    }

    /**
     * 更新车辆
     */
    public void updateBicycle(Bicycle bicycle) {
        bicycleMapper.updateById(bicycle);
    }

    /**
     * 更新车辆状态
     */
    public void updateStatus(Long id, Integer status, Long stationId) {
        bicycleMapper.updateStatus(id, status, stationId);
    }

    /**
     * 删除车辆
     */
    public void deleteBicycle(Long id) {
        bicycleMapper.deleteById(id);
    }

    /**
     * 统计各状态车辆数量
     */
    public long countByStatus(Integer status) {
        LambdaQueryWrapper<Bicycle> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Bicycle::getStatus, status);
        }
        return bicycleMapper.selectCount(wrapper);
    }
}
