package com.xiaou.ailearning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.Course;
import com.xiaou.ailearning.entity.KnowledgePoint;
import com.xiaou.ailearning.entity.LearningPath;
import com.xiaou.ailearning.entity.LearningRecord;
import com.xiaou.ailearning.entity.QaRecord;
import com.xiaou.ailearning.entity.User;
import com.xiaou.ailearning.entity.UserBehavior;
import com.xiaou.ailearning.mapper.CourseMapper;
import com.xiaou.ailearning.mapper.KnowledgePointMapper;
import com.xiaou.ailearning.mapper.LearningPathMapper;
import com.xiaou.ailearning.mapper.LearningRecordMapper;
import com.xiaou.ailearning.mapper.QaRecordMapper;
import com.xiaou.ailearning.mapper.UserBehaviorMapper;
import com.xiaou.ailearning.mapper.UserMapper;
import com.xiaou.ailearning.service.CourseService;
import com.xiaou.ailearning.service.LearningAnalysisService;
import com.xiaou.ailearning.service.LearningPathService;
import com.xiaou.ailearning.service.QaService;
import com.xiaou.ailearning.service.RecommendationService;
import com.xiaou.ailearning.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FrontendAdapterController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd HH:mm");

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private QaService qaService;

    @Autowired
    private LearningPathService learningPathService;

    @Autowired
    private LearningAnalysisService learningAnalysisService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Autowired
    private LearningRecordMapper learningRecordMapper;

    @Autowired
    private LearningPathMapper learningPathMapper;

    @Autowired
    private UserBehaviorMapper userBehaviorMapper;

    @Autowired
    private QaRecordMapper qaRecordMapper;

    @Autowired
    private UserMapper userMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Long, List<Map<String, Object>>> courseComments = new ConcurrentHashMap<>();
    private final Map<Long, Set<Long>> courseFavorites = new ConcurrentHashMap<>();
    private final AtomicLong commentIdGenerator = new AtomicLong(7000);

    @GetMapping("/user/check-username")
    public Result<Void> checkUsername(@RequestParam String username) {
        if (username == null || username.trim().length() < 3) {
            return Result.error("用户名至少需要 3 个字符");
        }
        return userService.findByUsername(username.trim()) == null ? Result.success("用户名可用") : Result.error("用户名已存在");
    }

    @GetMapping("/user/check-email")
    public Result<Void> checkEmail(@RequestParam String email) {
        if (email == null || email.trim().isEmpty()) {
            return Result.error("邮箱不能为空");
        }
        boolean exists = userService.lambdaQuery().eq(User::getEmail, email.trim()).count() > 0;
        return exists ? Result.error("邮箱已被注册") : Result.success("邮箱可用");
    }

    @GetMapping("/user/achievements/recent")
    public Result<List<Map<String, Object>>> getRecentAchievements(
            HttpServletRequest request,
            @RequestParam(defaultValue = "3") int limit) {
        return Result.success(buildAchievements(resolveUserId(request), Math.max(limit, 1)));
    }

    @GetMapping("/user/skill-tree")
    public Result<List<Map<String, Object>>> getSkillTree(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Map<Long, Double> masteryByKnowledge = buildKnowledgeMasteryMap(userId);
        List<KnowledgePoint> knowledgePoints = knowledgePointMapper.selectList(null).stream()
                .sorted(Comparator.comparing(KnowledgePoint::getCourseId, Comparator.nullsLast(Long::compareTo))
                        .thenComparing(KnowledgePoint::getDifficultyLevel, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(KnowledgePoint::getId))
                .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();
        double bestMastery = masteryByKnowledge.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        for (KnowledgePoint point : knowledgePoints) {
            double mastery = masteryByKnowledge.getOrDefault(point.getId(), 0.0);
            boolean unlocked = mastery > 0 || point.getDifficultyLevel() == null || point.getDifficultyLevel() <= 2 || bestMastery >= 0.55;

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", point.getId());
            item.put("name", point.getPointName());
            item.put("icon", resolveSkillIcon(point));
            item.put("mastered", mastery >= 0.7);
            item.put("unlocked", unlocked);
            item.put("masteryLevel", mastery);
            result.add(item);
        }
        return Result.success(result);
    }

    @PostMapping("/user/favorites/course/{courseId}")
    public Result<Map<String, Object>> addCourseFavorite(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Set<Long> favorites = courseFavorites.computeIfAbsent(userId, key -> ConcurrentHashMap.newKeySet());
        favorites.add(courseId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("favorite", true);
        data.put("courseId", courseId);
        data.put("favoriteCount", favorites.size());
        return Result.success("收藏成功", data);
    }

    @GetMapping("/course/progress")
    public Result<List<Map<String, Object>>> getCourseProgress(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<LearningRecord> records = getUserRecords(userId);
        Map<Long, List<LearningRecord>> groupedRecords = records.stream()
                .filter(record -> record.getCourseId() != null)
                .collect(Collectors.groupingBy(LearningRecord::getCourseId));

        List<Map<String, Object>> progressList = new ArrayList<>();
        for (Map.Entry<Long, List<LearningRecord>> entry : groupedRecords.entrySet()) {
            Course course = courseMapper.selectById(entry.getKey());
            if (course == null) {
                continue;
            }

            List<LearningRecord> courseRecords = entry.getValue();
            LearningRecord latestRecord = courseRecords.stream()
                    .filter(record -> record.getCreateTime() != null)
                    .max(Comparator.comparing(LearningRecord::getCreateTime))
                    .orElse(courseRecords.get(0));

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", course.getId());
            item.put("courseName", course.getCourseName());
            item.put("description", course.getDescription());
            item.put("progress", courseRecords.stream()
                    .map(LearningRecord::getCompletionRate)
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0));
            item.put("lastStudyTime", latestRecord.getCreateTime() != null ? latestRecord.getCreateTime().format(SHORT_TIME_FORMATTER) : "未开始");
            progressList.add(item);
        }

        progressList.sort(Comparator.comparing(
                item -> Objects.toString(item.get("lastStudyTime"), ""),
                Comparator.reverseOrder()
        ));
        return Result.success(progressList);
    }

    @GetMapping("/user-behavior/recent")
    public Result<List<Map<String, Object>>> getRecentBehaviors(
            HttpServletRequest request,
            @RequestParam(defaultValue = "6") int limit) {
        Long userId = resolveUserId(request);
        List<Map<String, Object>> items = userBehaviorMapper.selectByUserId(userId).stream()
                .limit(Math.max(limit, 1))
                .map(this::convertBehaviorView)
                .collect(Collectors.toList());
        return Result.success(items);
    }

    @GetMapping("/learning-record/calendar")
    public Result<List<Integer>> getLearningCalendar(
            HttpServletRequest request,
            @RequestParam int year,
            @RequestParam int month) {
        Long userId = resolveUserId(request);
        YearMonth target = YearMonth.of(year, month);
        List<Integer> days = getUserRecords(userId).stream()
                .map(LearningRecord::getCreateTime)
                .filter(Objects::nonNull)
                .filter(time -> YearMonth.from(time).equals(target))
                .map(LocalDateTime::getDayOfMonth)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return Result.success(days);
    }

    @GetMapping("/course/detail/{courseId}")
    public Result<Map<String, Object>> getCourseDetailView(@PathVariable Long courseId, HttpServletRequest request) {
        Course course = courseService.getCourseDetail(courseId);
        if (course == null) {
            return Result.error("课程不存在");
        }

        Long userId = resolveUserId(request);
        List<LearningRecord> courseRecords = learningRecordMapper.selectByCourseId(courseId);
        double progress = getCourseProgressValue(userId, courseId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", course.getId());
        data.put("courseName", course.getCourseName());
        data.put("description", course.getDescription());
        data.put("categoryId", course.getCategoryId());
        data.put("categoryName", resolveCategoryName(course.getCategoryId()));
        data.put("difficultyLevel", course.getDifficultyLevel());
        data.put("durationMinutes", course.getDurationMinutes());
        data.put("learningObjectives", course.getLearningObjectives());
        data.put("targetAudience", resolveTargetAudience(course));
        data.put("courseIntro", buildCourseIntro(course));
        data.put("studentCount", Math.max(12, countDistinctUsers(courseRecords)));
        data.put("progress", progress);
        data.put("status", course.getStatus());
        return Result.success(data);
    }

    @GetMapping("/course/{courseId}/knowledge-points")
    public Result<List<Map<String, Object>>> getCourseKnowledgePoints(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Map<Long, Double> masteryMap = buildKnowledgeMasteryMap(userId);
        List<Map<String, Object>> points = knowledgePointMapper.selectByCourseId(courseId).stream()
                .sorted(Comparator.comparing(KnowledgePoint::getDifficultyLevel, Comparator.nullsLast(Integer::compareTo)))
                .map(point -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", point.getId());
                    item.put("pointName", point.getPointName());
                    item.put("description", point.getDescription());
                    item.put("learningTimeEstimate", point.getLearningTimeEstimate());
                    item.put("difficultyLevel", point.getDifficultyLevel());
                    item.put("masteryLevel", masteryMap.getOrDefault(point.getId(), 0.0));
                    return item;
                })
                .collect(Collectors.toList());
        return Result.success(points);
    }

    @GetMapping("/course/{courseId}/learning-path")
    public Result<Map<String, Object>> getCourseLearningPath(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Map<String, Object> pathView = findPathForCourse(userId, courseId);
        return Result.success(pathView);
    }

    @GetMapping("/course/{courseId}/comments")
    public Result<List<Map<String, Object>>> getCourseComments(@PathVariable Long courseId) {
        ensureSeedComments(courseId);
        List<Map<String, Object>> comments = new ArrayList<>(courseComments.getOrDefault(courseId, Collections.emptyList()));
        comments.sort(Comparator.comparing(
                item -> Objects.toString(item.get("createTime"), ""),
                Comparator.reverseOrder()
        ));
        return Result.success(comments);
    }

    @PostMapping("/course/{courseId}/comments")
    public Result<Map<String, Object>> submitCourseComment(
            @PathVariable Long courseId,
            @RequestParam String content,
            HttpServletRequest request) {
        if (content == null || content.trim().isEmpty()) {
            return Result.error("评论内容不能为空");
        }

        User user = resolveUser(request);
        Map<String, Object> comment = new LinkedHashMap<>();
        comment.put("id", commentIdGenerator.incrementAndGet());
        comment.put("courseId", courseId);
        comment.put("authorName", user != null && user.getRealName() != null ? user.getRealName() : "学习者");
        comment.put("content", content.trim());
        comment.put("createTime", LocalDateTime.now().format(DATE_TIME_FORMATTER));
        comment.put("likes", 0);

        courseComments.computeIfAbsent(courseId, key -> Collections.synchronizedList(new ArrayList<>())).add(comment);
        return Result.success("评论发表成功", comment);
    }

    @PostMapping("/course/comments/{commentId}/like")
    public Result<Map<String, Object>> likeComment(@PathVariable Long commentId) {
        for (List<Map<String, Object>> comments : courseComments.values()) {
            for (Map<String, Object> comment : comments) {
                if (Objects.equals(asLong(comment.get("id")), commentId)) {
                    int likes = ((Number) comment.getOrDefault("likes", 0)).intValue() + 1;
                    comment.put("likes", likes);
                    return Result.success("点赞成功", comment);
                }
            }
        }
        return Result.error("评论不存在");
    }

    @GetMapping("/recommendation/related-courses/{courseId}")
    public Result<List<Course>> getRelatedCourses(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Course currentCourse = courseMapper.selectById(courseId);
        if (currentCourse == null) {
            return Result.success(Collections.emptyList());
        }

        List<Course> result = new ArrayList<>();
        Set<Long> added = new LinkedHashSet<>();
        List<Course> recommendedCourses = recommendationService.recommendCourses(userId, 6);
        for (Course course : recommendedCourses) {
            if (!Objects.equals(course.getId(), courseId) && added.add(course.getId())) {
                result.add(course);
            }
        }

        if (currentCourse.getCategoryId() != null) {
            List<Course> categoryCourses = courseService.getCoursesByCategory(currentCourse.getCategoryId());
            for (Course course : categoryCourses) {
                if (!Objects.equals(course.getId(), courseId) && added.add(course.getId())) {
                    result.add(course);
                }
            }
        }

        if (result.size() < 4) {
            for (Course hotCourse : courseService.getHotCourses(6)) {
                if (!Objects.equals(hotCourse.getId(), courseId) && added.add(hotCourse.getId())) {
                    result.add(hotCourse);
                }
            }
        }

        return Result.success(result.stream().limit(4).collect(Collectors.toList()));
    }

    @GetMapping("/learning-record/course/{courseId}/stats")
    public Result<Map<String, Object>> getCourseLearningStats(@PathVariable Long courseId, HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<LearningRecord> records = learningRecordMapper.selectByUserAndCourse(userId, courseId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalLearningTime", records.stream()
                .map(LearningRecord::getDurationSeconds)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum() / 60);
        data.put("avgMasteryLevel", records.stream()
                .map(LearningRecord::getMasteryLevel)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0));
        data.put("recordCount", records.size());
        return Result.success(data);
    }

    @PostMapping("/learning-record/start")
    public Result<Map<String, Object>> startLearningRecord(
            @RequestParam Long courseId,
            HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<KnowledgePoint> points = knowledgePointMapper.selectByCourseId(courseId);

        LearningRecord record = new LearningRecord();
        record.setUserId(userId);
        record.setCourseId(courseId);
        record.setKnowledgePointId(points.isEmpty() ? null : points.get(0).getId());
        record.setLearningType(1);
        record.setStartTime(LocalDateTime.now());
        record.setEndTime(LocalDateTime.now().plusMinutes(30));
        record.setDurationSeconds(1800);
        record.setCompletionRate(Math.max(getCourseProgressValue(userId, courseId), 15.0));
        record.setMasteryLevel(points.isEmpty() ? 0.35 : buildKnowledgeMasteryMap(userId).getOrDefault(points.get(0).getId(), 0.35));
        record.setScore(85.0);
        record.setInteractionCount(3);
        record.setFocusDuration(1500);
        learningRecordMapper.insert(record);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("courseId", courseId);
        data.put("recordId", record.getId());
        return Result.success("学习开始记录成功", data);
    }

    @GetMapping("/analysis/dashboard-stats")
    public Result<Map<String, Object>> getDashboardStats(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<LearningRecord> records = getUserRecords(userId);
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        int todayMinutes = sumMinutes(records, today, today);
        int yesterdayMinutes = sumMinutes(records, yesterday, yesterday);
        int weeklyMinutes = sumMinutes(records, today.minusDays(6), today);

        Map<String, Object> statistics = learningAnalysisService.calculateLearningStatistics(userId);
        double avgMastery = toRatio(statistics.get("avgMasteryLevel"));

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("todayStudyTime", todayMinutes);
        data.put("completedTasks", records.stream()
                .filter(record -> record.getCompletionRate() != null && record.getCompletionRate() >= 80)
                .count());
        data.put("avgMasteryLevel", avgMastery);
        data.put("totalPoints", Math.round(weeklyMinutes * 0.6 + avgMastery * 100 + getUserQaRecords(userId).size() * 5));
        data.put("studyStreak", calculateStudyStreak(records));
        data.put("weekProgress", Math.min(100, weeklyMinutes / 6.0));
        data.put("studyTimeTrend", yesterdayMinutes == 0 ? (todayMinutes > 0 ? 100.0 : 0.0) : ((todayMinutes - yesterdayMinutes) * 100.0 / yesterdayMinutes));
        return Result.success(data);
    }

    @GetMapping("/recommendation/ai-suggestions")
    public Result<List<Map<String, Object>>> getAiSuggestions(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<Map<String, Object>> suggestions = new ArrayList<>();

        List<Map<String, Object>> weaknesses = buildWeaknessItems(userId);
        if (!weaknesses.isEmpty()) {
            Map<String, Object> weakness = weaknesses.get(0);
            suggestions.add(createSuggestion(
                    "复习薄弱知识点",
                    "建议优先巩固 " + weakness.get("knowledgePointName") + "，降低错题率。",
                    "review_topic",
                    Objects.toString(weakness.get("knowledgePointId"), "")
            ));
        }

        List<Map<String, Object>> progressList = getCourseProgress(request).getData();
        if (!progressList.isEmpty()) {
            Map<String, Object> course = progressList.get(0);
            suggestions.add(createSuggestion(
                    "继续当前课程",
                    "您在 " + course.get("courseName") + " 上已有学习进度，继续完成可以更快形成闭环。",
                    "continue_course",
                    Objects.toString(course.get("id"), "")
            ));
        }

        List<Course> recommended = recommendationService.recommendCourses(userId, 2);
        if (!recommended.isEmpty()) {
            Course course = recommended.get(0);
            suggestions.add(createSuggestion(
                    "尝试新课程",
                    "推荐您开始《" + course.getCourseName() + "》，与当前掌握度更匹配。",
                    "study_course",
                    Objects.toString(course.getId(), "")
            ));
        }

        return Result.success(suggestions);
    }

    @GetMapping("/learning-path/current")
    public Result<Map<String, Object>> getCurrentLearningPath(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        LearningPath currentPath = learningPathMapper.selectActivePathsByUserId(userId).stream()
                .sorted(Comparator.comparing(LearningPath::getUpdateTime, Comparator.nullsLast(LocalDateTime::compareTo)).reversed())
                .findFirst()
                .orElse(null);

        if (currentPath == null) {
            return Result.success(null);
        }
        return Result.success(convertLearningPathView(currentPath));
    }

    @GetMapping("/learning-path/stats")
    public Result<Map<String, Object>> getLearningPathStats(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<LearningPath> paths = learningPathMapper.selectByUserId(userId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalPaths", paths.size());
        data.put("completedPaths", paths.stream().filter(path -> path.getProgressRate() != null && path.getProgressRate() >= 100).count());
        data.put("totalHours", paths.stream().map(LearningPath::getEstimatedDuration).filter(Objects::nonNull).mapToInt(Integer::intValue).sum());
        return Result.success(data);
    }

    @PostMapping("/learning-path/generate-ai")
    public Result<Map<String, Object>> generateAiLearningPath(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        KnowledgePoint targetPoint = selectRecommendedKnowledgePoint(userId);
        if (targetPoint == null) {
            return Result.error("暂无可生成的学习路径");
        }

        LearningPath path = learningPathService.createOptimalPath(userId, targetPoint.getId());
        return Result.success("AI学习路径生成成功", convertLearningPathView(path));
    }

    @PostMapping("/learning-path/{pathId}/optimize")
    public Result<Map<String, Object>> optimizeLearningPath(@PathVariable Long pathId) {
        LearningPath path = learningPathMapper.selectById(pathId);
        if (path == null) {
            return Result.error("学习路径不存在");
        }

        path.setOptimizationScore(Math.min(0.99, (path.getOptimizationScore() == null ? 0.75 : path.getOptimizationScore() + 0.05)));
        path.setUpdateTime(LocalDateTime.now());
        learningPathMapper.updateById(path);
        return Result.success("路径优化成功", convertLearningPathView(path));
    }

    @GetMapping("/recommendation/learning-paths")
    public Result<List<Map<String, Object>>> getLearningPathRecommendations(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<KnowledgePoint> knowledgePoints = recommendationService.recommendKnowledgePoints(userId, 4);
        List<Map<String, Object>> items = knowledgePoints.stream().map(point -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", point.getId());
            item.put("type", "learning_path");
            item.put("title", point.getPointName() + " 学习路径");
            item.put("description", "基于当前掌握度，为您推荐围绕 " + point.getPointName() + " 的阶段性学习路线。");
            return item;
        }).collect(Collectors.toList());
        return Result.success(items);
    }

    @GetMapping("/analysis/learning-path-stats")
    public Result<Map<String, Object>> getLearningPathAnalytics(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<LearningRecord> records = getUserRecords(userId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("weeklyProgress", Math.min(100.0, sumMinutes(records, LocalDate.now().minusDays(6), LocalDate.now()) / 6.0));
        data.put("averageMastery", Math.round(records.stream()
                .map(LearningRecord::getMasteryLevel)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0) * 100));
        return Result.success(data);
    }

    @GetMapping("/analysis/key-metrics")
    public Result<Map<String, Object>> getKeyMetrics(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<LearningRecord> scopedRecords = filterRecordsByPeriod(getUserRecords(userId), period);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalStudyTime", scopedRecords.stream().map(LearningRecord::getDurationSeconds).filter(Objects::nonNull).mapToInt(Integer::intValue).sum() / 60.0);
        data.put("avgCompletionRate", scopedRecords.stream().map(LearningRecord::getCompletionRate).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0) / 100.0);
        data.put("avgMasteryLevel", scopedRecords.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0));
        data.put("studyStreak", calculateStudyStreak(scopedRecords));
        data.put("overallProgress", Math.min(100.0, scopedRecords.stream().map(LearningRecord::getCompletionRate).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0)));
        data.put("trends", Map.of("studyTime", calculatePeriodTrend(getUserRecords(userId), period)));
        return Result.success(data);
    }

    @GetMapping("/analysis/study-time-trend")
    public Result<Map<String, Object>> getStudyTimeTrend(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period,
            @RequestParam(defaultValue = "daily") String granularity) {
        Long userId = resolveUserId(request);
        Map<String, Object> trendData = buildStudyTimeTrend(getUserRecords(userId), period, granularity);
        return Result.success(trendData);
    }

    @GetMapping("/analysis/effectiveness")
    public Result<Map<String, Object>> getEffectivenessData(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<LearningRecord> currentRecords = filterRecordsByPeriod(getUserRecords(userId), period);
        List<LearningRecord> previousRecords = filterPreviousPeriodRecords(getUserRecords(userId), period);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("currentPeriod", buildEffectivenessVector(currentRecords));
        data.put("previousPeriod", buildEffectivenessVector(previousRecords));
        return Result.success(data);
    }

    @GetMapping("/analysis/knowledge-heatmap")
    public Result<List<Map<String, Object>>> getKnowledgeHeatmap(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period,
            @RequestParam(required = false) String subject) {
        Long userId = resolveUserId(request);
        Map<Long, Double> mastery = buildKnowledgeMasteryMap(userId);
        List<Map<String, Object>> data = knowledgePointMapper.selectList(null).stream()
                .filter(point -> subject == null || subject.isBlank() || resolveCategoryName(point.getCourseId()).contains(subject))
                .sorted(Comparator.comparing(KnowledgePoint::getImportanceScore, Comparator.nullsLast(Double::compareTo)).reversed())
                .limit(30)
                .map(point -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", point.getId());
                    item.put("name", point.getPointName());
                    item.put("masteryLevel", mastery.getOrDefault(point.getId(), 0.0));
                    return item;
                })
                .collect(Collectors.toList());
        return Result.success(data);
    }

    @GetMapping("/analysis/time-distribution")
    public Result<Map<String, Object>> getTimeDistribution(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<LearningRecord> records = filterRecordsByPeriod(getUserRecords(userId), period);
        Map<Integer, Double> hoursByType = records.stream()
                .filter(record -> record.getLearningType() != null && record.getDurationSeconds() != null)
                .collect(Collectors.groupingBy(
                        LearningRecord::getLearningType,
                        Collectors.summingDouble(record -> record.getDurationSeconds() / 3600.0)
                ));

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("study", round(hoursByType.getOrDefault(1, 0.0) + hoursByType.getOrDefault(2, 0.0)));
        data.put("review", round(hoursByType.getOrDefault(4, 0.0)));
        data.put("practice", round(hoursByType.getOrDefault(3, 0.0)));
        data.put("break", round(Math.max(1.0, records.size() * 0.3)));
        return Result.success(data);
    }

    @GetMapping("/analysis/activity-pattern")
    public Result<Map<String, Object>> getActivityPattern(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<UserBehavior> behaviors = userBehaviorMapper.selectByUserId(userId);
        List<int[]> buckets = new ArrayList<>();
        Map<String, Integer> counter = new HashMap<>();

        for (UserBehavior behavior : behaviors) {
            if (behavior.getCreateTime() == null) {
                continue;
            }
            int x = behavior.getCreateTime().getHour();
            int y = behavior.getCreateTime().getDayOfWeek().getValue() - 1;
            String key = x + "-" + y;
            counter.merge(key, 20, Integer::sum);
        }

        for (int hour = 0; hour < 24; hour++) {
            for (int day = 0; day < 7; day++) {
                int value = Math.min(100, counter.getOrDefault(hour + "-" + day, 0));
                buckets.add(new int[]{hour, day, value});
            }
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("heatmapData", buckets);
        return Result.success(data);
    }

    @GetMapping("/analysis/ai-insights")
    public Result<List<Map<String, Object>>> getAiInsights(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<Map<String, Object>> insights = learningAnalysisService.generateLearningInsights(userId).stream()
                .map(this::convertInsightView)
                .collect(Collectors.toList());
        return Result.success(insights);
    }

    @GetMapping("/analysis/weakness")
    public Result<List<Map<String, Object>>> getWeaknessAnalysis(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        return Result.success(buildWeaknessItems(resolveUserId(request)));
    }

    @GetMapping("/analysis/recommendations")
    public Result<List<Map<String, Object>>> getImprovementRecommendations(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<Map<String, Object>> items = new ArrayList<>(getAiSuggestions(request).getData());

        List<Map<String, Object>> weaknesses = buildWeaknessItems(userId);
        if (!weaknesses.isEmpty()) {
            Map<String, Object> weakness = weaknesses.get(0);
            items.add(createSuggestion(
                    "进行针对练习",
                    "围绕 " + weakness.get("knowledgePointName") + " 安排一次专项练习，优先提升掌握度。",
                    "take_quiz",
                    Objects.toString(weakness.get("knowledgePointId"), "")
            ));
        }

        return Result.success(items.stream().limit(4).collect(Collectors.toList()));
    }

    @GetMapping("/analysis/peer-comparison")
    public Result<Map<String, Object>> getPeerComparison(
            HttpServletRequest request,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        List<LearningRecord> myRecords = filterRecordsByPeriod(getUserRecords(userId), period);
        List<LearningRecord> allRecords = learningRecordMapper.selectList(new LambdaQueryWrapper<>());

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("studyTime", buildComparisonMetric("学习时长", myRecords.stream().map(LearningRecord::getDurationSeconds).filter(Objects::nonNull).mapToInt(Integer::intValue).sum() / 60.0,
                allRecords.stream().map(LearningRecord::getDurationSeconds).filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(0.0) / 60.0));
        data.put("mastery", buildComparisonMetric("掌握水平",
                myRecords.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0) * 100,
                allRecords.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0) * 100));
        data.put("completion", buildComparisonMetric("完成效率",
                myRecords.stream().map(LearningRecord::getCompletionRate).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0),
                allRecords.stream().map(LearningRecord::getCompletionRate).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0)));
        return Result.success(data);
    }

    @GetMapping("/analysis/export")
    public ResponseEntity<String> exportAnalysisReport(
            HttpServletRequest request,
            @RequestParam(defaultValue = "pdf") String format,
            @RequestParam(defaultValue = "week") String period) {
        Long userId = resolveUserId(request);
        Map<String, Object> keyMetrics = getKeyMetrics(request, period).getData();
        StringBuilder content = new StringBuilder();
        content.append("AI 智能学习分析报告").append(System.lineSeparator())
                .append("周期: ").append(period).append(System.lineSeparator())
                .append("总学习时长(分钟): ").append(keyMetrics.get("totalStudyTime")).append(System.lineSeparator())
                .append("平均完成率: ").append(keyMetrics.get("avgCompletionRate")).append(System.lineSeparator())
                .append("平均掌握度: ").append(keyMetrics.get("avgMasteryLevel")).append(System.lineSeparator())
                .append("连续学习天数: ").append(keyMetrics.get("studyStreak")).append(System.lineSeparator())
                .append("导出时间: ").append(LocalDateTime.now().format(DATE_TIME_FORMATTER)).append(System.lineSeparator())
                .append("用户ID: ").append(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("analysis-report-" + period + "." + ("json".equalsIgnoreCase(format) ? "json" : "txt"), StandardCharsets.UTF_8)
                .build());
        return ResponseEntity.ok().headers(headers).body(content.toString());
    }

    @GetMapping("/qa/sessions")
    public Result<List<Map<String, Object>>> getQaSessions(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        Map<String, List<QaRecord>> grouped = getUserQaRecords(userId).stream()
                .filter(record -> record.getSessionId() != null && !record.getSessionId().isBlank())
                .collect(Collectors.groupingBy(QaRecord::getSessionId, LinkedHashMap::new, Collectors.toList()));

        List<Map<String, Object>> sessions = new ArrayList<>();
        for (Map.Entry<String, List<QaRecord>> entry : grouped.entrySet()) {
            QaRecord latest = entry.getValue().stream()
                    .max(Comparator.comparing(QaRecord::getCreateTime, Comparator.nullsLast(LocalDateTime::compareTo)))
                    .orElse(entry.getValue().get(0));

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("sessionId", entry.getKey());
            item.put("lastQuestion", latest.getQuestion());
            item.put("updateTime", latest.getCreateTime() != null ? latest.getCreateTime().format(DATE_TIME_FORMATTER) : LocalDateTime.now().format(DATE_TIME_FORMATTER));
            item.put("messageCount", entry.getValue().size() * 2);
            sessions.add(item);
        }

        sessions.sort(Comparator.comparing(item -> Objects.toString(item.get("updateTime"), ""), Comparator.reverseOrder()));
        return Result.success(sessions);
    }

    @GetMapping("/qa/sessions/{sessionId}/messages")
    public Result<List<Map<String, Object>>> getQaMessages(@PathVariable String sessionId) {
        List<Map<String, Object>> messages = new ArrayList<>();
        for (QaRecord record : qaService.getSessionHistory(sessionId)) {
            messages.add(createQaMessage("user", record.getQuestion(), record.getCreateTime()));
            messages.add(createQaMessage("ai", record.getAnswer(), record.getCreateTime() != null ? record.getCreateTime().plusSeconds(1) : LocalDateTime.now()));
        }
        return Result.success(messages);
    }

    @PostMapping("/qa/session/start")
    public Result<Map<String, Object>> startQaSession(
            HttpServletRequest request,
            @RequestParam(required = false) String sessionId) {
        String actualSessionId = sessionId == null || sessionId.isBlank()
                ? "session_" + System.currentTimeMillis()
                : sessionId;

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("sessionId", actualSessionId);
        data.put("userId", resolveUserId(request));
        return Result.success("会话创建成功", data);
    }

    @PostMapping("/qa/rate")
    public Result<Void> rateQaAnswer(@RequestParam String sessionId, @RequestParam Integer rating) {
        List<QaRecord> records = qaService.getSessionHistory(sessionId);
        if (records.isEmpty()) {
            return Result.error("会话不存在");
        }

        QaRecord latest = records.get(records.size() - 1);
        qaService.saveFeedback(latest.getId(), rating != null && rating > 0);
        return Result.success("反馈已记录");
    }

    @GetMapping("/qa/stats")
    public Result<Map<String, Object>> getQaStats(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        List<QaRecord> records = getUserQaRecords(userId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalQuestions", records.size());
        data.put("solvedQuestions", records.stream()
                .filter(record -> record.getAnswerScore() != null && record.getAnswerScore() >= 0.6)
                .count());
        data.put("positiveRate", records.stream()
                .filter(record -> record.getIsHelpful() != null)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list ->
                        list.isEmpty() ? 0.0 : list.stream().filter(QaRecord::getIsHelpful).count() * 1.0 / list.size())));
        return Result.success(data);
    }

    @GetMapping("/qa/export")
    public ResponseEntity<String> exportQaHistory(HttpServletRequest request) {
        Long userId = resolveUserId(request);
        StringBuilder content = new StringBuilder();
        content.append("AI 问答导出").append(System.lineSeparator()).append(System.lineSeparator());
        for (QaRecord record : getUserQaRecords(userId)) {
            content.append("时间: ").append(record.getCreateTime() != null ? record.getCreateTime().format(DATE_TIME_FORMATTER) : "-").append(System.lineSeparator())
                    .append("问题: ").append(record.getQuestion()).append(System.lineSeparator())
                    .append("回答: ").append(record.getAnswer()).append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("qa-history.txt", StandardCharsets.UTF_8)
                .build());
        return ResponseEntity.ok().headers(headers).body(content.toString());
    }

    private Long resolveUserId(HttpServletRequest request) {
        if (request != null) {
            Object value = request.getAttribute("userId");
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            if (value instanceof String && !((String) value).isBlank()) {
                return Long.parseLong((String) value);
            }
        }
        return 1L;
    }

    private User resolveUser(HttpServletRequest request) {
        User user = userService.getById(resolveUserId(request));
        if (user == null) {
            user = userService.findByUsername("admin");
        }
        return user;
    }

    private List<LearningRecord> getUserRecords(Long userId) {
        return learningRecordMapper.selectByUserId(userId).stream()
                .sorted(Comparator.comparing(LearningRecord::getCreateTime, Comparator.nullsLast(LocalDateTime::compareTo)).reversed())
                .collect(Collectors.toList());
    }

    private List<QaRecord> getUserQaRecords(Long userId) {
        return qaRecordMapper.selectByUserId(userId).stream()
                .sorted(Comparator.comparing(QaRecord::getCreateTime, Comparator.nullsLast(LocalDateTime::compareTo)).reversed())
                .collect(Collectors.toList());
    }

    private Map<String, Object> convertBehaviorView(UserBehavior behavior) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", behavior.getId());
        item.put("behaviorType", behavior.getBehaviorType());
        item.put("createTime", behavior.getCreateTime() != null ? behavior.getCreateTime().format(DATE_TIME_FORMATTER) : LocalDateTime.now().format(DATE_TIME_FORMATTER));
        item.put("description", resolveBehaviorDescription(behavior));
        return item;
    }

    private String resolveBehaviorDescription(UserBehavior behavior) {
        if (behavior == null) {
            return "发生了一次学习行为";
        }
        if ("login".equalsIgnoreCase(behavior.getBehaviorType())) {
            return "登录了学习平台";
        }
        if ("page_view".equalsIgnoreCase(behavior.getBehaviorType()) && Objects.equals("course", behavior.getTargetType())) {
            Course course = courseMapper.selectById(behavior.getTargetId());
            return "浏览了课程《" + (course != null ? course.getCourseName() : "未知课程") + "》";
        }
        if ("study".equalsIgnoreCase(behavior.getBehaviorType())) {
            Course course = courseMapper.selectById(behavior.getTargetId());
            return "完成了一段《" + (course != null ? course.getCourseName() : "课程") + "》学习";
        }
        if ("comment".equalsIgnoreCase(behavior.getBehaviorType())) {
            return "发表了一条课程评论";
        }
        return "完成了 " + behavior.getBehaviorType() + " 行为";
    }

    private List<Map<String, Object>> buildAchievements(Long userId, int limit) {
        List<LearningRecord> records = getUserRecords(userId);
        List<QaRecord> qaRecords = getUserQaRecords(userId);
        List<Map<String, Object>> achievements = new ArrayList<>();

        if (!records.isEmpty()) {
            achievements.add(createAchievement("学习连续打卡", "连续保持学习习惯，形成稳定节奏。", "fa-fire", LocalDateTime.now().minusDays(1)));
        }
        if (records.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0) >= 0.75) {
            achievements.add(createAchievement("知识掌握达人", "平均掌握度达到 75% 以上。", "fa-graduation-cap", LocalDateTime.now().minusHours(8)));
        }
        if (!qaRecords.isEmpty()) {
            achievements.add(createAchievement("AI 提问积极分子", "已经多次使用智能问答解决学习问题。", "fa-robot", LocalDateTime.now().minusHours(2)));
        }
        if (records.size() >= 3) {
            achievements.add(createAchievement("课程推进者", "已在多个课程中形成稳定进度。", "fa-trophy", LocalDateTime.now().minusDays(2)));
        }

        return achievements.stream().limit(limit).collect(Collectors.toList());
    }

    private Map<String, Object> createAchievement(String name, String description, String icon, LocalDateTime earnedTime) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("name", name);
        item.put("description", description);
        item.put("icon", icon);
        item.put("earnedTime", earnedTime.format(DATE_TIME_FORMATTER));
        return item;
    }

    private String resolveSkillIcon(KnowledgePoint point) {
        if (point == null || point.getCourseId() == null) {
            return "fa-brain";
        }
        if (Objects.equals(point.getCourseId(), 1L)) {
            return "fa-code";
        }
        if (Objects.equals(point.getCourseId(), 2L)) {
            return "fa-diagram-project";
        }
        if (Objects.equals(point.getCourseId(), 3L)) {
            return "fa-globe";
        }
        if (Objects.equals(point.getCourseId(), 4L)) {
            return "fa-database";
        }
        return "fa-brain";
    }

    private Map<Long, Double> buildKnowledgeMasteryMap(Long userId) {
        return getUserRecords(userId).stream()
                .filter(record -> record.getKnowledgePointId() != null && record.getMasteryLevel() != null)
                .collect(Collectors.groupingBy(
                        LearningRecord::getKnowledgePointId,
                        Collectors.collectingAndThen(Collectors.toList(), list ->
                                list.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0))
                ));
    }

    private int countDistinctUsers(Collection<LearningRecord> records) {
        return (int) records.stream()
                .map(LearningRecord::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    private double getCourseProgressValue(Long userId, Long courseId) {
        return learningRecordMapper.selectByUserAndCourse(userId, courseId).stream()
                .map(LearningRecord::getCompletionRate)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private String resolveCategoryName(Long categoryId) {
        if (categoryId == null) {
            return "综合课程";
        }
        if (categoryId == 101L) {
            return "编程基础";
        }
        if (categoryId == 102L) {
            return "算法与数据结构";
        }
        if (categoryId == 103L) {
            return "前端开发";
        }
        if (categoryId == 104L) {
            return "数据库";
        }
        if (categoryId == 105L) {
            return "人工智能";
        }
        return "专业课程";
    }

    private String resolveTargetAudience(Course course) {
        int difficulty = course.getDifficultyLevel() == null ? 1 : course.getDifficultyLevel();
        if (difficulty <= 2) {
            return "适合零基础到初级学习者";
        }
        if (difficulty == 3) {
            return "适合已具备基础知识的进阶学习者";
        }
        return "适合希望系统提升能力的高阶学习者";
    }

    private String buildCourseIntro(Course course) {
        return (course.getDescription() == null ? "" : course.getDescription())
                + (course.getLearningObjectives() == null ? "" : "<br><br>学习目标：" + course.getLearningObjectives());
    }

    private Map<String, Object> findPathForCourse(Long userId, Long courseId) {
        List<LearningPath> paths = learningPathMapper.selectByUserId(userId);
        Set<Long> courseKnowledgeIds = knowledgePointMapper.selectByCourseId(courseId).stream()
                .map(KnowledgePoint::getId)
                .collect(Collectors.toSet());

        for (LearningPath path : paths) {
            List<Map<String, Object>> steps = parsePathSteps(path.getPathSteps());
            boolean matched = steps.stream().map(step -> asLong(step.get("knowledgePointId"))).anyMatch(courseKnowledgeIds::contains);
            if (matched) {
                return convertLearningPathView(path);
            }
        }

        List<Map<String, Object>> steps = knowledgePointMapper.selectByCourseId(courseId).stream()
                .map(point -> {
                    Map<String, Object> step = new LinkedHashMap<>();
                    step.put("id", point.getId());
                    step.put("stepName", point.getPointName());
                    step.put("description", point.getDescription());
                    step.put("estimatedTime", point.getLearningTimeEstimate());
                    return step;
                })
                .collect(Collectors.toList());

        Map<String, Object> pathView = new LinkedHashMap<>();
        pathView.put("id", courseId);
        pathView.put("pathName", "课程学习路径");
        pathView.put("currentStep", 0);
        pathView.put("steps", steps);
        return pathView;
    }

    private Map<String, Object> convertLearningPathView(LearningPath path) {
        Map<String, Object> item = new LinkedHashMap<>();
        List<Map<String, Object>> steps = parsePathSteps(path.getPathSteps()).stream()
                .map(step -> {
                    Map<String, Object> converted = new LinkedHashMap<>();
                    Long knowledgePointId = asLong(step.get("knowledgePointId"));
                    KnowledgePoint point = knowledgePointId == null ? null : knowledgePointMapper.selectById(knowledgePointId);
                    converted.put("id", knowledgePointId);
                    converted.put("stepName", Objects.toString(step.getOrDefault("pointName", point != null ? point.getPointName() : "学习步骤"), "学习步骤"));
                    converted.put("description", point != null ? point.getDescription() : "按照推荐顺序完成该步骤。");
                    converted.put("estimatedTime", asInt(step.getOrDefault("estimatedTime", point != null ? point.getLearningTimeEstimate() : 60), 60));
                    converted.put("difficulty", asInt(step.getOrDefault("difficultyLevel", point != null ? point.getDifficultyLevel() : 2), 2));
                    converted.put("stepType", "知识学习");
                    converted.put("progress", Boolean.TRUE.equals(step.get("completed")) ? 100 : 0);
                    converted.put("completed", Boolean.TRUE.equals(step.get("completed")));
                    converted.put("prerequisites", Collections.emptyList());
                    return converted;
                })
                .collect(Collectors.toList());

        item.put("id", path.getId());
        item.put("pathName", path.getPathName());
        item.put("targetKnowledgePoint", resolveKnowledgePointName(path.getTargetKnowledgePointId()));
        item.put("estimatedDuration", normalizePathDuration(path.getEstimatedDuration()));
        item.put("progressRate", path.getProgressRate() == null ? 0.0 : path.getProgressRate());
        item.put("optimizationScore", path.getOptimizationScore() == null ? 0.0 : path.getOptimizationScore());
        item.put("difficulty", steps.stream().map(step -> asInt(step.get("difficulty"), 2)).max(Integer::compareTo).orElse(2));
        item.put("currentStep", path.getCurrentStep() == null ? 0 : path.getCurrentStep());
        item.put("pathSteps", steps);
        return item;
    }

    private int normalizePathDuration(Integer estimatedDuration) {
        if (estimatedDuration == null) {
            return 240;
        }
        return estimatedDuration < 24 ? estimatedDuration * 60 : estimatedDuration;
    }

    private List<Map<String, Object>> parsePathSteps(String rawPathSteps) {
        if (rawPathSteps == null || rawPathSteps.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(rawPathSteps, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception ignored) {
            return Collections.emptyList();
        }
    }

    private KnowledgePoint selectRecommendedKnowledgePoint(Long userId) {
        Set<Long> learnedIds = getUserRecords(userId).stream()
                .map(LearningRecord::getKnowledgePointId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return knowledgePointMapper.selectList(null).stream()
                .filter(point -> !learnedIds.contains(point.getId()))
                .sorted(Comparator.comparing(KnowledgePoint::getImportanceScore, Comparator.nullsLast(Double::compareTo)).reversed())
                .findFirst()
                .orElse(knowledgePointMapper.selectList(null).stream().findFirst().orElse(null));
    }

    private int sumMinutes(List<LearningRecord> records, LocalDate start, LocalDate end) {
        return records.stream()
                .filter(record -> record.getCreateTime() != null)
                .filter(record -> !record.getCreateTime().toLocalDate().isBefore(start) && !record.getCreateTime().toLocalDate().isAfter(end))
                .map(LearningRecord::getDurationSeconds)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum() / 60;
    }

    private int calculateStudyStreak(List<LearningRecord> records) {
        Set<LocalDate> days = records.stream()
                .map(LearningRecord::getCreateTime)
                .filter(Objects::nonNull)
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (days.isEmpty()) {
            return 0;
        }

        int streak = 0;
        LocalDate cursor = LocalDate.now();
        while (days.contains(cursor) || (streak == 0 && days.contains(cursor.minusDays(1)))) {
            if (days.contains(cursor)) {
                streak++;
            }
            cursor = cursor.minusDays(1);
            if (!days.contains(cursor) && streak > 0) {
                break;
            }
        }
        return Math.max(streak, days.contains(LocalDate.now().minusDays(1)) ? 1 : 0);
    }

    private double toRatio(Object value) {
        if (!(value instanceof Number)) {
            return 0.0;
        }
        double number = ((Number) value).doubleValue();
        return number > 1 ? number / 100.0 : number;
    }

    private double calculatePeriodTrend(List<LearningRecord> records, String period) {
        List<LearningRecord> current = filterRecordsByPeriod(records, period);
        List<LearningRecord> previous = filterPreviousPeriodRecords(records, period);
        double currentMinutes = current.stream().map(LearningRecord::getDurationSeconds).filter(Objects::nonNull).mapToInt(Integer::intValue).sum() / 60.0;
        double previousMinutes = previous.stream().map(LearningRecord::getDurationSeconds).filter(Objects::nonNull).mapToInt(Integer::intValue).sum() / 60.0;
        if (previousMinutes == 0) {
            return currentMinutes > 0 ? 100.0 : 0.0;
        }
        return round((currentMinutes - previousMinutes) * 100.0 / previousMinutes);
    }

    private List<LearningRecord> filterRecordsByPeriod(List<LearningRecord> records, String period) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = startOfPeriod(period, end);
        return records.stream()
                .filter(record -> record.getCreateTime() != null)
                .filter(record -> !record.getCreateTime().isBefore(start) && !record.getCreateTime().isAfter(end))
                .collect(Collectors.toList());
    }

    private List<LearningRecord> filterPreviousPeriodRecords(List<LearningRecord> records, String period) {
        LocalDateTime end = startOfPeriod(period, LocalDateTime.now()).minusSeconds(1);
        LocalDateTime start = startOfPeriod(period, end);
        return records.stream()
                .filter(record -> record.getCreateTime() != null)
                .filter(record -> !record.getCreateTime().isBefore(start) && !record.getCreateTime().isAfter(end))
                .collect(Collectors.toList());
    }

    private LocalDateTime startOfPeriod(String period, LocalDateTime reference) {
        if ("month".equalsIgnoreCase(period)) {
            return reference.toLocalDate().minusDays(29).atStartOfDay();
        }
        if ("year".equalsIgnoreCase(period)) {
            return reference.toLocalDate().minusDays(364).atStartOfDay();
        }
        return reference.toLocalDate().minusDays(6).atStartOfDay();
    }

    private Map<String, Object> buildStudyTimeTrend(List<LearningRecord> records, String period, String granularity) {
        List<String> dates = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        LocalDate start = startOfPeriod(period, LocalDateTime.now()).toLocalDate();

        for (int i = 0; i < 7; i++) {
            LocalDate day = start.plusDays(i);
            dates.add(day.format(DateTimeFormatter.ofPattern("MM-dd")));
            double minutes = records.stream()
                    .filter(record -> record.getCreateTime() != null && record.getCreateTime().toLocalDate().equals(day))
                    .map(LearningRecord::getDurationSeconds)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum() / 60.0;
            values.add(round(minutes));
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("dates", dates);
        data.put("values", values);
        return data;
    }

    private List<Integer> buildEffectivenessVector(List<LearningRecord> records) {
        double mastery = records.stream().map(LearningRecord::getMasteryLevel).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0) * 100;
        double completion = records.stream().map(LearningRecord::getCompletionRate).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0.0);
        double focus = records.stream()
                .filter(record -> record.getFocusDuration() != null && record.getDurationSeconds() != null && record.getDurationSeconds() > 0)
                .mapToDouble(record -> record.getFocusDuration() * 100.0 / record.getDurationSeconds())
                .average()
                .orElse(60.0);
        double efficiency = records.stream()
                .filter(record -> record.getMasteryLevel() != null && record.getDurationSeconds() != null && record.getDurationSeconds() > 0)
                .mapToDouble(record -> record.getMasteryLevel() * 100.0 / (record.getDurationSeconds() / 3600.0))
                .average()
                .orElse(55.0);
        double consistency = Math.min(100.0, calculateStudyStreak(records) * 18.0);
        double accuracy = records.stream().map(LearningRecord::getScore).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(75.0);

        return List.of(
                (int) round(mastery),
                (int) round(completion),
                (int) round(focus),
                (int) round(Math.min(100.0, efficiency)),
                (int) round(consistency),
                (int) round(accuracy)
        );
    }

    private List<Map<String, Object>> buildWeaknessItems(Long userId) {
        Map<Long, Double> masteryMap = buildKnowledgeMasteryMap(userId);
        List<Long> weakIds = learningAnalysisService.identifyWeakKnowledgePoints(userId);
        if (weakIds.isEmpty()) {
            weakIds = masteryMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .limit(3)
                    .collect(Collectors.toList());
        }

        List<Map<String, Object>> items = new ArrayList<>();
        for (Long id : weakIds) {
            KnowledgePoint point = knowledgePointMapper.selectById(id);
            if (point == null) {
                continue;
            }

            double mastery = masteryMap.getOrDefault(id, 0.3);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("knowledgePointId", point.getId());
            item.put("knowledgePointName", point.getPointName());
            item.put("description", point.getDescription());
            item.put("masteryLevel", mastery);
            item.put("errorRate", Math.max(0.15, 1 - mastery));
            item.put("severity", Math.max(0.2, 1 - mastery));
            items.add(item);
        }
        return items;
    }

    private Map<String, Object> createSuggestion(String title, String description, String action, String params) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("title", title);
        item.put("description", description);
        item.put("action", action);
        item.put("params", params);
        return item;
    }

    private Map<String, Object> convertInsightView(Map<String, Object> source) {
        Map<String, Object> target = new LinkedHashMap<>(source);
        String type = Objects.toString(source.get("type"), "info");
        if ("positive".equals(type)) {
            target.put("type", "achievement");
        } else if ("warning".equals(type)) {
            target.put("type", "warning");
        } else {
            target.put("type", "suggestion");
        }
        return target;
    }

    private Map<String, Object> buildComparisonMetric(String label, double userValue, double avgValue) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("label", label);
        item.put("userValue", round(userValue));
        item.put("avgValue", round(Math.max(avgValue, 1.0)));
        return item;
    }

    private void ensureSeedComments(Long courseId) {
        courseComments.computeIfAbsent(courseId, key -> Collections.synchronizedList(new ArrayList<>()));
        if (!courseComments.get(courseId).isEmpty()) {
            return;
        }

        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            return;
        }

        courseComments.get(courseId).add(createSeedComment(courseId, "系统推荐", "这门课程的知识结构很清晰，适合作为阶段性复习。", 4));
        courseComments.get(courseId).add(createSeedComment(courseId, "张三", "案例比较贴近实际开发，做完练习后理解更扎实。", 7));
    }

    private Map<String, Object> createSeedComment(Long courseId, String authorName, String content, int likes) {
        Map<String, Object> comment = new LinkedHashMap<>();
        comment.put("id", commentIdGenerator.incrementAndGet());
        comment.put("courseId", courseId);
        comment.put("authorName", authorName);
        comment.put("content", content);
        comment.put("createTime", LocalDateTime.now().minusHours(likes).format(DATE_TIME_FORMATTER));
        comment.put("likes", likes);
        return comment;
    }

    private Map<String, Object> createQaMessage(String type, String content, LocalDateTime createTime) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("type", type);
        item.put("content", content);
        item.put("createTime", createTime != null ? createTime.format(DATE_TIME_FORMATTER) : LocalDateTime.now().format(DATE_TIME_FORMATTER));
        return item;
    }

    private String resolveKnowledgePointName(Long knowledgePointId) {
        KnowledgePoint point = knowledgePointId == null ? null : knowledgePointMapper.selectById(knowledgePointId);
        return point != null ? point.getPointName() : "目标知识点";
    }

    private Long asLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            return Long.parseLong((String) value);
        }
        return null;
    }

    private int asInt(Object value, int defaultValue) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            return Integer.parseInt((String) value);
        }
        return defaultValue;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
