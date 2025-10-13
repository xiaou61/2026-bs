package com.xiaou.campusvideo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.service.*;
import com.xiaou.campusvideo.util.FileUtil;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private VideoLikeService videoLikeService;
    
    @Autowired
    private VideoCollectService videoCollectService;
    
    @Autowired
    private VideoShareService videoShareService;
    
    @Autowired
    private VideoReportService videoReportService;
    
    @Autowired
    private FileUtil fileUtil;
    
    @PostMapping("/upload")
    public Result<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String videoUrl = fileUtil.uploadVideo(file);
            return Result.success(videoUrl);
        } catch (Exception e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/uploadCover")
    public Result<String> uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String coverUrl = fileUtil.uploadCover(file);
            return Result.success(coverUrl);
        } catch (Exception e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/publish")
    public Result<?> publishVideo(@RequestBody Map<String, Object> params) {
        try {
            Video video = new Video();
            video.setUserId(UserHolder.getUserId());
            video.setTitle((String) params.get("title"));
            video.setDescription((String) params.get("description"));
            video.setVideoUrl((String) params.get("videoUrl"));
            video.setCoverUrl((String) params.get("coverUrl"));
            video.setLocation((String) params.get("location"));
            video.setPermission((Integer) params.get("permission"));
            
            List<Long> topicIds = (List<Long>) params.get("topicIds");
            
            videoService.publishVideo(video, topicIds);
            return Result.success("发布成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/recommend")
    public Result<IPage<Video>> getRecommendVideos(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<Video> videoPage = new Page<>(page, size);
        IPage<Video> result = videoService.getRecommendVideos(videoPage);
        return Result.success(result);
    }
    
    @GetMapping("/following")
    public Result<IPage<Video>> getFollowingVideos(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Long userId = UserHolder.getUserId();
        Page<Video> videoPage = new Page<>(page, size);
        IPage<Video> result = videoService.getFollowingVideos(videoPage, userId);
        return Result.success(result);
    }
    
    @GetMapping("/{id}")
    public Result<Video> getVideoDetail(@PathVariable Long id) {
        Video video = videoService.getVideoDetail(id);
        if (video == null) {
            return Result.error("视频不存在");
        }
        return Result.success(video);
    }
    
    @DeleteMapping("/{id}")
    public Result<?> deleteVideo(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        Video video = videoService.getById(id);
        
        if (!video.getUserId().equals(userId)) {
            return Result.error("无权限删除");
        }
        
        video.setStatus(3);
        videoService.updateById(video);
        return Result.success("删除成功");
    }
    
    @PostMapping("/{id}/like")
    public Result<?> likeVideo(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        videoLikeService.like(userId, id);
        return Result.success("点赞成功");
    }
    
    @DeleteMapping("/{id}/like")
    public Result<?> unlikeVideo(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        videoLikeService.unlike(userId, id);
        return Result.success("取消点赞成功");
    }
    
    @PostMapping("/{id}/collect")
    public Result<?> collectVideo(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        videoCollectService.collect(userId, id);
        return Result.success("收藏成功");
    }
    
    @DeleteMapping("/{id}/collect")
    public Result<?> uncollectVideo(@PathVariable Long id) {
        Long userId = UserHolder.getUserId();
        videoCollectService.uncollect(userId, id);
        return Result.success("取消收藏成功");
    }
    
    @PostMapping("/{id}/share")
    public Result<?> shareVideo(@PathVariable Long id, @RequestBody Map<String, String> params) {
        Long userId = UserHolder.getUserId();
        String shareText = params.get("shareText");
        videoShareService.share(userId, id, shareText);
        return Result.success("转发成功");
    }
    
    @PostMapping("/{id}/play")
    public Result<?> recordPlay(@PathVariable Long id) {
        videoService.increasePlayCount(id);
        return Result.success();
    }
    
    @PostMapping("/{id}/report")
    public Result<?> reportVideo(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String reportType = params.get("reportType");
        String reportReason = params.get("reportReason");
        videoReportService.reportVideo(id, reportType, reportReason);
        return Result.success("举报成功");
    }
    
    @GetMapping("/search")
    public Result<IPage<Video>> searchVideos(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Page<Video> videoPage = new Page<>(page, size);
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w
                .like(Video::getTitle, keyword)
                .or()
                .like(Video::getDescription, keyword))
               .eq(Video::getStatus, 1)
               .orderByDesc(Video::getHeatScore);
        
        IPage<Video> result = videoService.page(videoPage, wrapper);
        return Result.success(result);
    }
}


