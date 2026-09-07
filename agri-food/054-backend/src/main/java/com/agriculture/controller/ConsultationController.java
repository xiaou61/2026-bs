package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.Consultation;
import com.agriculture.entity.ConsultationAnswer;
import com.agriculture.service.ConsultationService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping("/page")
    public Result<Page<Consultation>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) Integer status) {
        return Result.success(consultationService.getPage(pageNum, pageSize, title, status));
    }

    @GetMapping("/{id}")
    public Result<Consultation> getById(@PathVariable Long id) {
        consultationService.incrementView(id);
        return Result.success(consultationService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Consultation consultation, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        consultation.setQuestionerId(userId);
        consultationService.save(consultation);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Consultation consultation) {
        consultationService.updateById(consultation);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        consultationService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}/answers")
    public Result<List<ConsultationAnswer>> getAnswers(@PathVariable Long id) {
        return Result.success(consultationService.getAnswers(id));
    }

    @PostMapping("/{id}/answer")
    public Result<?> addAnswer(@PathVariable Long id, @RequestBody ConsultationAnswer answer, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        answer.setConsultationId(id);
        answer.setAnswererId(userId);
        consultationService.addAnswer(answer);
        return Result.success();
    }

    @PutMapping("/answer/{id}/adopt")
    public Result<?> adoptAnswer(@PathVariable Long id) {
        consultationService.adoptAnswer(id);
        return Result.success();
    }
}
