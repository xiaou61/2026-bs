package com.psychologycare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.psychologycare.common.Result;
import com.psychologycare.entity.AssessmentQuestionnaire;
import com.psychologycare.service.AuthService;
import com.psychologycare.service.AssessmentQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questionnaire")
@RequiredArgsConstructor
public class AssessmentQuestionnaireController {
    private final AuthService authService;
    private final AssessmentQuestionnaireService service;

    @GetMapping("/page")
    public Result<IPage<AssessmentQuestionnaire>> page(@RequestAttribute String role,
                                                       @RequestParam(required = false) Integer pageNum,
                                                       @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody AssessmentQuestionnaire entity) {
        authService.assertAdminOrTeacherOrStudent(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody AssessmentQuestionnaire entity) {
        authService.assertAdminOrTeacherOrStudent(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTeacherOrStudent(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTeacher(role);
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTeacher(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}







