package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.MailSendRecord;
import com.phishingtraining.service.AuthService;
import com.phishingtraining.service.MailSendRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/send-record")
@RequiredArgsConstructor
public class MailSendRecordController {
    private final AuthService authService;
    private final MailSendRecordService service;

    @GetMapping("/page")
    public Result<IPage<MailSendRecord>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody MailSendRecord entity) {
        authService.assertAdminOrSecurity(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody MailSendRecord entity) {
        authService.assertAdminOrSecurity(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/sent/{id}")
    public Result<Void> sent(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSecurity(role);
        service.updateStatus(id, "SENT");
        return Result.success();
    }

    @PutMapping("/open/{id}")
    public Result<Void> open(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSecurity(role);
        service.updateStatus(id, "OPENED");
        return Result.success();
    }

    @PutMapping("/fail/{id}")
    public Result<Void> fail(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrSecurity(role);
        service.updateStatus(id, "FAILED");
        return Result.success();
    }

}
