package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.dto.ElderCreateDTO;
import com.xiaou.seniorhealth.repository.ElderRepository;
import com.xiaou.seniorhealth.security.CurrentUserSupport;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/elders")
public class ElderController {
    private final ElderRepository elderRepository;
    private final CurrentUserSupport currentUserSupport;

    public ElderController(ElderRepository elderRepository, CurrentUserSupport currentUserSupport) {
        this.elderRepository = elderRepository;
        this.currentUserSupport = currentUserSupport;
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<Elder> create(@Valid @RequestBody ElderCreateDTO dto) {
        Elder elder = new Elder();
        BeanUtils.copyProperties(dto, elder);
        elder.setCreatedAt(LocalDateTime.now());
        Elder saved = elderRepository.save(elder);
        return ApiResponse.ok(saved);
    }

    @PreAuthorize("hasRole('ELDER')")
    @GetMapping("/me")
    public ApiResponse<Elder> myProfile() {
        Elder elder = currentUserSupport.getCurrentElderProfile();
        if (elder == null) {
            return ApiResponse.fail("elder profile not found");
        }
        return ApiResponse.ok(elder);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ApiResponse<Elder> detail(@PathVariable Long id) {
        Elder e = elderRepository.findById(id).orElse(null);
        return ApiResponse.ok(e);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ApiResponse<List<Elder>> list() {
        Iterable<Elder> all = elderRepository.findAll();
        List<Elder> list = new ArrayList<>();
        all.forEach(list::add);
        return ApiResponse.ok(list);
    }
}
