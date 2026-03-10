package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.StudyPlan;
import com.gongkao.service.StudyPlanService;
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
                                        @RequestParam(required = false) String title) {
        return Result.success(studyPlanService.getList(pageNum, pageSize, userId, subjectId, status, title));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody StudyPlan studyPlan) {
        studyPlanService.add(studyPlan);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody StudyPlan studyPlan) {
        studyPlanService.update(studyPlan);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        studyPlanService.delete(id);
        return Result.success();
    }
}
