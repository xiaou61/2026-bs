package com.psychology.system.controller;

import com.psychology.system.common.Result;
import com.psychology.system.entity.AssessmentRecord;
import com.psychology.system.entity.Question;
import com.psychology.system.entity.Scale;
import com.psychology.system.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @GetMapping("/scales")
    public Result<List<Scale>> getScales() {
        return Result.success(assessmentService.getScales());
    }

    @GetMapping("/scales/{id}")
    public Result<Scale> getScaleById(@PathVariable Long id) {
        return Result.success(assessmentService.getScaleById(id));
    }

    @GetMapping("/scales/{scaleId}/questions")
    public Result<List<Question>> getQuestions(@PathVariable Long scaleId) {
        return Result.success(assessmentService.getQuestionsByScaleId(scaleId));
    }

    @PostMapping
    public Result<AssessmentRecord> submitAssessment(
            @RequestHeader("userId") Long userId,
            @RequestParam Long scaleId,
            @RequestParam String answers) {
        return Result.success(assessmentService.submitAssessment(userId, scaleId, answers));
    }

    @GetMapping("/my")
    public Result<List<AssessmentRecord>> getMyAssessments(@RequestHeader("userId") Long userId) {
        return Result.success(assessmentService.getMyAssessments(userId));
    }
}
