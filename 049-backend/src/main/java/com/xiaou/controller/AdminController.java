package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final SubjectMapper subjectMapper;
    private final CourseMapper courseMapper;
    private final QuestionMapper questionMapper;
    private final QuestionCategoryMapper categoryMapper;
    private final NoticeMapper noticeMapper;

    @GetMapping("/dashboard")
    public Result<?> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userService.count());
        data.put("courseCount", courseMapper.selectCount(null));
        data.put("questionCount", questionMapper.selectCount(null));
        return Result.success(data);
    }

    // 用户管理
    @GetMapping("/users")
    public Result<?> users(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Integer role) {
        return Result.success(userService.getPage(current, size, keyword, role));
    }

    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = userService.getById(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.success();
    }

    // 科目管理
    @GetMapping("/subjects")
    public Result<?> subjects() {
        return Result.success(subjectMapper.selectList(new LambdaQueryWrapper<Subject>().orderByAsc(Subject::getSortOrder)));
    }

    @PostMapping("/subject")
    public Result<?> createSubject(@RequestBody Subject subject) {
        subjectMapper.insert(subject);
        return Result.success();
    }

    @PutMapping("/subject/{id}")
    public Result<?> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        subject.setId(id);
        subjectMapper.updateById(subject);
        return Result.success();
    }

    // 课程管理
    @GetMapping("/courses")
    public Result<?> courses(@RequestParam(defaultValue = "1") int current,
                            @RequestParam(defaultValue = "10") int size) {
        return Result.success(courseMapper.selectPage(new Page<>(current, size), null));
    }

    @PostMapping("/course")
    public Result<?> createCourse(@RequestBody Course course) {
        courseMapper.insert(course);
        return Result.success();
    }

    @PutMapping("/course/{id}")
    public Result<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        courseMapper.updateById(course);
        return Result.success();
    }

    // 题库分类管理
    @GetMapping("/categories")
    public Result<?> categories(@RequestParam(required = false) Long subjectId) {
        LambdaQueryWrapper<QuestionCategory> wrapper = new LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(QuestionCategory::getSubjectId, subjectId);
        return Result.success(categoryMapper.selectList(wrapper));
    }

    @PostMapping("/category")
    public Result<?> createCategory(@RequestBody QuestionCategory category) {
        categoryMapper.insert(category);
        return Result.success();
    }

    // 题目管理
    @GetMapping("/questions")
    public Result<?> questions(@RequestParam(defaultValue = "1") int current,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) Long subjectId) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(Question::getSubjectId, subjectId);
        return Result.success(questionMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @PostMapping("/question")
    public Result<?> createQuestion(@RequestBody Question question) {
        questionMapper.insert(question);
        return Result.success();
    }

    @PutMapping("/question/{id}")
    public Result<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        question.setId(id);
        questionMapper.updateById(question);
        return Result.success();
    }

    @DeleteMapping("/question/{id}")
    public Result<?> deleteQuestion(@PathVariable Long id) {
        questionMapper.deleteById(id);
        return Result.success();
    }

    // 公告管理
    @GetMapping("/notices")
    public Result<?> notices(@RequestParam(defaultValue = "1") int current,
                            @RequestParam(defaultValue = "10") int size) {
        return Result.success(noticeMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<Notice>().orderByDesc(Notice::getCreateTime)));
    }

    @PostMapping("/notice")
    public Result<?> createNotice(@RequestBody Notice notice) {
        noticeMapper.insert(notice);
        return Result.success();
    }

    @PutMapping("/notice/{id}")
    public Result<?> updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        noticeMapper.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/notice/{id}")
    public Result<?> deleteNotice(@PathVariable Long id) {
        noticeMapper.deleteById(id);
        return Result.success();
    }
}
