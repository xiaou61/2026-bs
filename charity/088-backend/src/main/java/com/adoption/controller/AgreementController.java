package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.AdoptionAgreement;
import com.adoption.service.AgreementService;
import com.adoption.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(agreementService.page(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("role") String role, @RequestBody AdoptionAgreement agreement) {
        authService.assertStaff(role);
        agreementService.add(agreement);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestAttribute("role") String role, @RequestBody AdoptionAgreement agreement) {
        authService.assertStaff(role);
        agreementService.update(agreement);
        return Result.success();
    }
}
