package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.BusinessException;
import com.gongkao.common.Result;
import com.gongkao.entity.StudyPlan;
import com.gongkao.service.StudyPlanService;
import com.gongkao.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/plan")
public class StudyPlanController {

    @Autowired
    private StudyPlanService studyPlanService;

    @GetMapping("/list")
    public Result<Page<StudyPlan>> list(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) Long userId,
                                        @RequestParam(required = false) Long subjectId,
                                        @RequestParam(required = false) Integer status,
                                        @RequestParam(required = false) String title,
                                        HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            userId = AuthUtils.getUserId(request);
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        return Result.success(studyPlanService.getList(pageNum, pageSize, userId, subjectId, status, title));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody StudyPlan studyPlan, HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            studyPlan.setUserId(AuthUtils.getUserId(request));
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        studyPlanService.add(studyPlan);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody StudyPlan studyPlan, HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            StudyPlan current = studyPlanService.getById(studyPlan.getId());
            if (!AuthUtils.getUserId(request).equals(current.getUserId())) {
                throw new BusinessException(403, "无权操作他人学习计划");
            }
            studyPlan.setUserId(current.getUserId());
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        studyPlanService.update(studyPlan);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            StudyPlan current = studyPlanService.getById(id);
            if (!AuthUtils.getUserId(request).equals(current.getUserId())) {
                throw new BusinessException(403, "无权操作他人学习计划");
            }
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        studyPlanService.delete(id);
        return Result.success();
    }
}
