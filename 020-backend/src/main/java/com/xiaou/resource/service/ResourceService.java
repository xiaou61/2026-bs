package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.entity.Resource;
import com.xiaou.resource.entity.ResourceRating;
import com.xiaou.resource.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceRatingService resourceRatingService;

    @Autowired
    private PointsRecordService pointsRecordService;

    public boolean uploadResource(Resource resource, Long userId) {
        resource.setUserId(userId);
        resource.setDownloadCount(0);
        resource.setViewCount(0);
        resource.setRating(0.0);
        resource.setRatingCount(0);
        resource.setStatus("approved");
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        return this.save(resource);
    }

    public IPage<Resource> getResourceList(Integer page, Integer size, String category, String keyword) {
        Page<Resource> pageParam = new Page<>(page, size);
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "approved");
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("title", keyword).or().like("description", keyword);
        }
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public Resource getResourceDetail(Long id) {
        Resource resource = this.getById(id);
        if (resource != null) {
            resource.setViewCount(resource.getViewCount() + 1);
            this.updateById(resource);
        }
        return resource;
    }

    public boolean downloadResource(Long resourceId, Long userId) {
        Resource resource = this.getById(resourceId);
        if (resource == null) {
            return false;
        }

        if (resource.getPoints() > 0) {
            boolean deducted = userService.deductPoints(userId, resource.getPoints());
            if (!deducted) {
                return false;
            }
            
            userService.addPoints(resource.getUserId(), resource.getPoints());

            PointsRecord record = new PointsRecord();
            record.setUserId(userId);
            record.setPoints(-resource.getPoints());
            record.setType("download");
            record.setDescription("下载资源：" + resource.getTitle());
            record.setCreateTime(LocalDateTime.now());
            pointsRecordService.save(record);
        }

        resource.setDownloadCount(resource.getDownloadCount() + 1);
        this.updateById(resource);
        return true;
    }

    public boolean rateResource(Long resourceId, Long userId, Integer rating, String comment) {
        ResourceRating resourceRating = new ResourceRating();
        resourceRating.setResourceId(resourceId);
        resourceRating.setUserId(userId);
        resourceRating.setRating(rating);
        resourceRating.setComment(comment);
        resourceRating.setCreateTime(LocalDateTime.now());
        resourceRatingService.save(resourceRating);

        Resource resource = this.getById(resourceId);
        if (resource != null) {
            double newRating = (resource.getRating() * resource.getRatingCount() + rating) / (resource.getRatingCount() + 1);
            resource.setRating(newRating);
            resource.setRatingCount(resource.getRatingCount() + 1);
            resource.setUpdateTime(LocalDateTime.now());
            this.updateById(resource);
        }
        return true;
    }

    public IPage<Resource> getMyResources(Long userId, Integer page, Integer size) {
        Page<Resource> pageParam = new Page<>(page, size);
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }
}

