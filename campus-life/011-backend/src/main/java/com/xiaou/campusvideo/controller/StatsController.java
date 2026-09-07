package com.xiaou.campusvideo.controller;

import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.entity.UserPointsLog;
import com.xiaou.campusvideo.entity.Video;
import com.xiaou.campusvideo.service.UserPointsLogService;
import com.xiaou.campusvideo.service.UserService;
import com.xiaou.campusvideo.service.VideoService;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserPointsLogService userPointsLogService;
    
    @GetMapping("/creator")
    public Result<Map<String, Object>> getCreatorStats() {
        Long userId = UserHolder.getUserId();
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        List<Video> videos = videoService.lambdaQuery()
                .eq(Video::getUserId, userId)
                .eq(Video::getStatus, 1)
                .orderByDesc(Video::getPublishTime)
                .list();
        List<UserPointsLog> pointsLogs = userPointsLogService.getPointsLog(userId);
        
        long totalPlays = videos.stream().mapToLong(video -> safe(video.getPlayCount())).sum();
        long totalLikes = videos.stream().mapToLong(video -> safe(video.getLikeCount())).sum();
        long totalComments = videos.stream().mapToLong(video -> safe(video.getCommentCount())).sum();
        long totalShares = videos.stream().mapToLong(video -> safe(video.getShareCount())).sum();
        long totalCollects = videos.stream().mapToLong(video -> safe(video.getCollectCount())).sum();
        BigDecimal averageHeat = videos.isEmpty()
                ? BigDecimal.ZERO
                : videos.stream()
                        .map(video -> video.getHeatScore() == null ? BigDecimal.ZERO : video.getHeatScore())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(videos.size()), 2, RoundingMode.HALF_UP);
        
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("videoCount", videos.size());
        summary.put("totalPlays", totalPlays);
        summary.put("totalLikes", totalLikes);
        summary.put("totalComments", totalComments);
        summary.put("totalShares", totalShares);
        summary.put("totalCollects", totalCollects);
        summary.put("fansCount", safe(user.getFansCount()));
        summary.put("followCount", safe(user.getFollowCount()));
        summary.put("points", safe(user.getPoints()));
        summary.put("level", safe(user.getLevel()));
        summary.put("averageHeat", averageHeat);
        summary.put("weeklyVideoCount", countVideosInRecentDays(videos, 7));
        
        Map<String, Object> data = new HashMap<>();
        data.put("summary", summary);
        data.put("publishTrend", buildPublishTrend(videos, 7));
        data.put("interactionTrend", buildInteractionTrend(videos, 7));
        data.put("recentVideos", enrichVideos(videos.stream().limit(6).collect(Collectors.toList())));
        data.put("topVideos", enrichVideos(videos.stream()
                .sorted(Comparator.comparing(
                        Video::getHeatScore,
                        Comparator.nullsFirst(BigDecimal::compareTo)
                ).reversed())
                .limit(5)
                .collect(Collectors.toList())));
        data.put("recentPoints", pointsLogs.stream().limit(8).collect(Collectors.toList()));
        
        return Result.success(data);
    }
    
    @GetMapping("/video/{id}")
    public Result<Map<String, Object>> getVideoStats(@PathVariable Long id) {
        Video video = videoService.getById(id);
        if (video == null) {
            return Result.error("视频不存在");
        }
        
        Long currentUserId = UserHolder.getUserId();
        User currentUser = userService.getById(currentUserId);
        boolean isAdmin = currentUser != null && "ADMIN".equals(currentUser.getRole());
        if (!video.getUserId().equals(currentUserId) && !isAdmin) {
            return Result.error(403, "无权查看该视频数据");
        }
        
        Video detail = videoService.getVideoDetail(id);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", detail.getId());
        data.put("title", detail.getTitle());
        data.put("coverUrl", detail.getCoverUrl());
        data.put("videoUrl", detail.getVideoUrl());
        data.put("publishTime", videoService.resolvePublishTime(detail));
        data.put("playCount", safe(detail.getPlayCount()));
        data.put("likeCount", safe(detail.getLikeCount()));
        data.put("commentCount", safe(detail.getCommentCount()));
        data.put("shareCount", safe(detail.getShareCount()));
        data.put("collectCount", safe(detail.getCollectCount()));
        data.put("heatScore", detail.getHeatScore() == null ? BigDecimal.ZERO : detail.getHeatScore());
        data.put("permission", detail.getPermission());
        data.put("status", detail.getStatus());
        data.put("engagementRate", calculateEngagementRate(detail));
        data.put("topicNames", detail.getTopics() == null
                ? new ArrayList<>()
                : detail.getTopics().stream().map(topic -> topic.getTopicName()).collect(Collectors.toList()));
        
        return Result.success(data);
    }
    
    private List<Video> enrichVideos(List<Video> videos) {
        List<Video> result = new ArrayList<>();
        for (Video video : videos) {
            Video detail = videoService.getVideoDetail(video.getId());
            if (detail != null) {
                result.add(detail);
            }
        }
        return result;
    }
    
    private List<Map<String, Object>> buildPublishTrend(List<Video> videos, int days) {
        Map<LocalDate, Long> countMap = videos.stream()
                .collect(Collectors.groupingBy(video -> videoService.resolvePublishTime(video).toLocalDate(), Collectors.counting()));
        return buildDailySeries(days, countMap, "count");
    }
    
    private List<Map<String, Object>> buildInteractionTrend(List<Video> videos, int days) {
        Map<LocalDate, Long> countMap = videos.stream()
                .collect(Collectors.groupingBy(
                        video -> videoService.resolvePublishTime(video).toLocalDate(),
                        Collectors.summingLong(video ->
                                safe(video.getLikeCount()) +
                                safe(video.getCommentCount()) +
                                safe(video.getShareCount()) +
                                safe(video.getCollectCount()))
                ));
        return buildDailySeries(days, countMap, "count");
    }
    
    private List<Map<String, Object>> buildDailySeries(int days, Map<LocalDate, Long> countMap, String valueKey) {
        List<Map<String, Object>> trend = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("date", date.format(formatter));
            item.put(valueKey, countMap.getOrDefault(date, 0L));
            trend.add(item);
        }
        return trend;
    }
    
    private long countVideosInRecentDays(List<Video> videos, int days) {
        LocalDate startDate = LocalDate.now().minusDays(days - 1);
        return videos.stream()
                .filter(video -> !videoService.resolvePublishTime(video).toLocalDate().isBefore(startDate))
                .count();
    }
    
    private BigDecimal calculateEngagementRate(Video video) {
        long interactions = safe(video.getLikeCount()) + safe(video.getCommentCount()) + safe(video.getShareCount()) + safe(video.getCollectCount());
        long plays = safe(video.getPlayCount());
        if (plays == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(interactions)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(plays), 2, RoundingMode.HALF_UP);
    }
    
    private int safe(Integer value) {
        return value == null ? 0 : value;
    }
}
