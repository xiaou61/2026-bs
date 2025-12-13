package com.xiaou.health.controller;

import com.xiaou.health.common.Result;
import com.xiaou.health.dto.ConsultationCreateRequest;
import com.xiaou.health.entity.Consultation;
import com.xiaou.health.service.ConsultationService;
import com.xiaou.health.util.UserContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PostMapping
    public Result<Consultation> createConsultation(@Valid @RequestBody ConsultationCreateRequest request) {
        try {
            Long userId = UserContext.getUserId();
            Consultation consultation = consultationService.createConsultation(userId, request);
            return Result.success(consultation);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/patient")
    public Result<List<Consultation>> getPatientConsultations() {
        try {
            Long userId = UserContext.getUserId();
            List<Consultation> list = consultationService.getPatientConsultations(userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/doctor")
    public Result<List<Consultation>> getDoctorConsultations() {
        try {
            Long userId = UserContext.getUserId();
            List<Consultation> list = consultationService.getDoctorConsultations(userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/doctor/pending")
    public Result<List<Consultation>> getDoctorPendingConsultations() {
        try {
            Long userId = UserContext.getUserId();
            List<Consultation> list = consultationService.getDoctorPendingConsultations(userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Consultation> getConsultationDetail(@PathVariable Long id) {
        try {
            Consultation consultation = consultationService.getConsultationDetail(id);
            return Result.success(consultation);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/answer")
    public Result<Consultation> answerConsultation(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            Long userId = UserContext.getUserId();
            String answer = request.get("answer");
            Consultation consultation = consultationService.answerConsultation(id, answer, userId);
            return Result.success(consultation);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/rate")
    public Result<Consultation> rateConsultation(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Long userId = UserContext.getUserId();
            Integer rating = (Integer) request.get("rating");
            String feedback = (String) request.get("feedback");
            Consultation consultation = consultationService.rateConsultation(id, rating, feedback, userId);
            return Result.success(consultation);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
