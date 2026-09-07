package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.domain.FollowUp;
import com.xiaou.seniorhealth.dto.FollowUpCreateDTO;
import com.xiaou.seniorhealth.repository.FollowUpRepository;
import com.xiaou.seniorhealth.security.CurrentUserSupport;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/followups")
public class FollowUpController {
    private final FollowUpRepository followUpRepository;
    private final CurrentUserSupport currentUserSupport;

    public FollowUpController(FollowUpRepository followUpRepository, CurrentUserSupport currentUserSupport) {
        this.followUpRepository = followUpRepository;
        this.currentUserSupport = currentUserSupport;
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<FollowUp> create(@Valid @RequestBody FollowUpCreateDTO dto) {
        FollowUp f = new FollowUp();
        BeanUtils.copyProperties(dto, f);
        f.setStatus("PENDING");
        f.setCreatedAt(LocalDateTime.now());
        return ApiResponse.ok(followUpRepository.save(f));
    }

    @PreAuthorize("hasRole('ELDER')")
    @GetMapping("/me")
    public ApiResponse<List<FollowUp>> myFollowUps() {
        Elder elder = currentUserSupport.getCurrentElderProfile();
        if (elder == null) {
            return ApiResponse.fail("elder profile not found");
        }
        return ApiResponse.ok(followUpRepository.findByElder(elder.getId()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/elder/{elderId}")
    public ApiResponse<List<FollowUp>> byElder(@PathVariable Long elderId) {
        return ApiResponse.ok(followUpRepository.findByElder(elderId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/doctor/{doctorUserId}/range")
    public ApiResponse<List<FollowUp>> range(@PathVariable Long doctorUserId, @RequestParam String from, @RequestParam String to) {
        LocalDate f = LocalDate.parse(from);
        LocalDate t = LocalDate.parse(to);
        return ApiResponse.ok(followUpRepository.findRangeByDoctor(doctorUserId, f, t));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping("/{id}/status")
    public ApiResponse<FollowUp> changeStatus(@PathVariable Long id, @RequestParam String status) {
        FollowUp f = followUpRepository.findById(id).orElse(null);
        if (f == null) return ApiResponse.fail("not found");
        f.setStatus(status);
        return ApiResponse.ok(followUpRepository.save(f));
    }
}
