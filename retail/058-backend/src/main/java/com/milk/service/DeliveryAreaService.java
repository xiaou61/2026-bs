package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.entity.DeliveryArea;
import com.milk.mapper.DeliveryAreaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeliveryAreaService {

    @Resource
    private DeliveryAreaMapper deliveryAreaMapper;

    public List<DeliveryArea> list() {
        return deliveryAreaMapper.selectList(new QueryWrapper<DeliveryArea>().eq("status", 1));
    }

    public Page<DeliveryArea> page(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<DeliveryArea> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        return deliveryAreaMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(DeliveryArea area) {
        if (area.getId() == null) {
            deliveryAreaMapper.insert(area);
        } else {
            deliveryAreaMapper.updateById(area);
        }
    }

    public void deleteById(Long id) {
        deliveryAreaMapper.deleteById(id);
    }
}
