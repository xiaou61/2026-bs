package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) Long subjectId,
                         @RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Integer isFree) {
        return Result.success(courseService.getPage(current, size, subjectId, keyword, isFree));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(courseService.getDetail(id));
    }

    @GetMapping("/{id}/chapters")
    public Result<?> getChapters(@PathVariable Long id) {
        return Result.success(courseService.getChapters(id));
    }
}
