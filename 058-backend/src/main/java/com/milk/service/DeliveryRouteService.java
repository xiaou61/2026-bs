package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.entity.DeliveryRoute;
import com.milk.mapper.DeliveryRouteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeliveryRouteService {

    @Resource
    private DeliveryRouteMapper deliveryRouteMapper;

    public List<DeliveryRoute> list() {
        return deliveryRouteMapper.selectList(new QueryWrapper<DeliveryRoute>().eq("status", 1));
    }

    public Page<DeliveryRoute> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<DeliveryRoute> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        return deliveryRouteMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(DeliveryRoute route) {
        if (route.getId() == null) {
            deliveryRouteMapper.insert(route);
        } else {
            deliveryRouteMapper.updateById(route);
        }
    }

    public void deleteById(Long id) {
        deliveryRouteMapper.deleteById(id);
    }
}
