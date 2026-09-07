package com.online.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.common.Result;
import com.online.entity.*;
import com.online.service.*;
import com.online.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private LearnRecordService learnRecordService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, DigestUtil.md5Hex(password))
                .eq(User::getRole, 2));
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("user", user);
        return Result.success(result);
    }

    @GetMapping("/stats")
    public Result<?> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userService.count());
        stats.put("courseCount", courseService.count());
        stats.put("learnCount", learnRecordService.count());
        stats.put("commentCount", commentService.count());
        return Result.success(stats);
    }

    @GetMapping("/user/list")
    public Result<?> getUserList(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(required = false) String keyword) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getUsername, keyword).or().like(User::getNickname, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> result = userService.page(page, wrapper);
        result.getRecords().forEach(u -> u.setPassword(null));
        return Result.success(result);
    }

    @PutMapping("/user/status")
    public Result<?> updateUserStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = (Integer) params.get("status");
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.success();
    }

    @GetMapping("/category/list")
    public Result<?> getCategoryList() {
        return Result.success(categoryService.getAdminList());
    }

    @PostMapping("/category/add")
    public Result<?> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping("/category/update")
    public Result<?> updateCategory(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }

    @GetMapping("/course/list")
    public Result<?> getCourseList(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false) String keyword) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Course::getTitle, keyword);
        }
        wrapper.orderByDesc(Course::getCreateTime);
        return Result.success(courseService.page(page, wrapper));
    }

    @PostMapping("/course/add")
    public Result<?> addCourse(@RequestBody Course course) {
        courseService.save(course);
        return Result.success();
    }

    @PutMapping("/course/update")
    public Result<?> updateCourse(@RequestBody Course course) {
        courseService.updateById(course);
        return Result.success();
    }

    @DeleteMapping("/course/{id}")
    public Result<?> deleteCourse(@PathVariable Long id) {
        courseService.removeById(id);
        return Result.success();
    }

    @PutMapping("/course/status")
    public Result<?> updateCourseStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = (Integer) params.get("status");
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        courseService.updateById(course);
        return Result.success();
    }

    @GetMapping("/course/{id}/chapters")
    public Result<?> getCourseChapters(@PathVariable Long id) {
        return Result.success(courseService.getChapters(id));
    }

    @PostMapping("/chapter/add")
    public Result<?> addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        Course course = courseService.getById(chapter.getCourseId());
        course.setChapterCount(course.getChapterCount() + 1);
        courseService.updateById(course);
        return Result.success();
    }

    @PutMapping("/chapter/update")
    public Result<?> updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.success();
    }

    @DeleteMapping("/chapter/{id}")
    public Result<?> deleteChapter(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        chapterService.removeById(id);
        videoService.remove(new LambdaQueryWrapper<Video>().eq(Video::getChapterId, id));
        Course course = courseService.getById(chapter.getCourseId());
        course.setChapterCount(Math.max(0, course.getChapterCount() - 1));
        courseService.updateById(course);
        return Result.success();
    }

    @PostMapping("/video/add")
    public Result<?> addVideo(@RequestBody Video video) {
        videoService.save(video);
        return Result.success();
    }

    @PutMapping("/video/update")
    public Result<?> updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return Result.success();
    }

    @DeleteMapping("/video/{id}")
    public Result<?> deleteVideo(@PathVariable Long id) {
        videoService.removeById(id);
        return Result.success();
    }

    @GetMapping("/comment/list")
    public Result<?> getCommentList(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(required = false) Integer status) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Comment::getStatus, status);
        }
        wrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> result = commentService.page(page, wrapper);
        result.getRecords().forEach(c -> {
            User user = userService.getById(c.getUserId());
            if (user != null) {
                c.setUsername(user.getNickname());
            }
            Course course = courseService.getById(c.getCourseId());
            if (course != null) {
                c.setCourseTitle(course.getTitle());
            }
        });
        return Result.success(result);
    }

    @PutMapping("/comment/status")
    public Result<?> updateCommentStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = (Integer) params.get("status");
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        commentService.updateById(comment);
        return Result.success();
    }

    @DeleteMapping("/comment/{id}")
    public Result<?> deleteComment(@PathVariable Long id) {
        commentService.removeById(id);
        return Result.success();
    }

    @GetMapping("/banner/list")
    public Result<?> getBannerList() {
        return Result.success(bannerService.list(new LambdaQueryWrapper<Banner>().orderByAsc(Banner::getSort)));
    }

    @PostMapping("/banner/add")
    public Result<?> addBanner(@RequestBody Banner banner) {
        bannerService.save(banner);
        return Result.success();
    }

    @PutMapping("/banner/update")
    public Result<?> updateBanner(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.success();
    }

    @DeleteMapping("/banner/{id}")
    public Result<?> deleteBanner(@PathVariable Long id) {
        bannerService.removeById(id);
        return Result.success();
    }
}
