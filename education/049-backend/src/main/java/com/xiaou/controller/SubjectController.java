package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Subject;
import com.xiaou.entity.QuestionCategory;
import com.xiaou.mapper.SubjectMapper;
import com.xiaou.mapper.QuestionCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectMapper subjectMapper;
    private final QuestionCategoryMapper categoryMapper;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(subjectMapper.selectList(new LambdaQueryWrapper<Subject>()
                .eq(Subject::getStatus, 1).orderByAsc(Subject::getSortOrder)));
    }

    @GetMapping("/{id}/categories")
    public Result<?> getCategories(@PathVariable Long id) {
        return Result.success(categoryMapper.selectList(new LambdaQueryWrapper<QuestionCategory>()
                .eq(QuestionCategory::getSubjectId, id).orderByAsc(QuestionCategory::getSortOrder)));
    }
}
