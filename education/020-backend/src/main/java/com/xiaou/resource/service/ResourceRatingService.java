package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.ResourceRating;
import com.xiaou.resource.mapper.ResourceRatingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceRatingService extends ServiceImpl<ResourceRatingMapper, ResourceRating> {

    public List<ResourceRating> getRatingsByResourceId(Long resourceId) {
        QueryWrapper<ResourceRating> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id", resourceId);
        wrapper.orderByDesc("create_time");
        return this.list(wrapper);
    }
}

