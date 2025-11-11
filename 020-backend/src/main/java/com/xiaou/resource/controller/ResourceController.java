package com.xiaou.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.Resource;
import com.xiaou.resource.entity.ResourceRating;
import com.xiaou.resource.service.ResourceRatingService;
import com.xiaou.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRatingService resourceRatingService;

    @PostMapping("/upload")
    public Result<String> uploadResource(@RequestAttribute Long userId, @RequestBody Resource resource) {
        boolean success = resourceService.uploadResource(resource, userId);
        return success ? Result.success("上传成功") : Result.error("上传失败");
    }

    @GetMapping("/list")
    public Result<IPage<Resource>> getResourceList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        IPage<Resource> pageData = resourceService.getResourceList(page, size, category, keyword);
        return Result.success(pageData);
    }

    @GetMapping("/{id}")
    public Result<Resource> getResourceDetail(@PathVariable Long id) {
        Resource resource = resourceService.getResourceDetail(id);
        return Result.success(resource);
    }

    @PostMapping("/download/{id}")
    public Result<String> downloadResource(@PathVariable Long id, @RequestAttribute Long userId) {
        boolean success = resourceService.downloadResource(id, userId);
        return success ? Result.success("下载成功") : Result.error("积分不足或资源不存在");
    }

    @PostMapping("/rate")
    public Result<String> rateResource(@RequestAttribute Long userId, @RequestBody Map<String, Object> params) {
        Long resourceId = Long.valueOf(params.get("resourceId").toString());
        Integer rating = Integer.valueOf(params.get("rating").toString());
        String comment = (String) params.get("comment");
        boolean success = resourceService.rateResource(resourceId, userId, rating, comment);
        return success ? Result.success("评价成功") : Result.error("评价失败");
    }

    @GetMapping("/ratings/{resourceId}")
    public Result<List<ResourceRating>> getRatings(@PathVariable Long resourceId) {
        List<ResourceRating> ratings = resourceRatingService.getRatingsByResourceId(resourceId);
        return Result.success(ratings);
    }

    @GetMapping("/my")
    public Result<IPage<Resource>> getMyResources(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<Resource> pageData = resourceService.getMyResources(userId, page, size);
        return Result.success(pageData);
    }
}

