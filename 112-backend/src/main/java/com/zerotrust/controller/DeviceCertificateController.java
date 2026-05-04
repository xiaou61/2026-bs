package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.DeviceCertificate;
import com.zerotrust.service.DeviceCertificateService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/certificate")
@RequiredArgsConstructor
public class DeviceCertificateController {
    private final DeviceCertificateService service;

    @GetMapping("/page")
    public Result<PageInfo<DeviceCertificate>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody DeviceCertificate entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody DeviceCertificate entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@PathVariable Long id) {
        service.updateStatus(id, "REVOKED");
        return Result.success();
    }

    @PutMapping("/renew/{id}")
    public Result<Void> renew(@PathVariable Long id) {
        service.updateStatus(id, "VALID");
        return Result.success();
    }

}
