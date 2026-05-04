package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.MailTemplate;
import com.phishingtraining.service.MailTemplateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class MailTemplateController {
    private final MailTemplateService service;

    @GetMapping("/page")
    public Result<IPage<MailTemplate>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody MailTemplate entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody MailTemplate entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@PathVariable Long id) {
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@PathVariable Long id) {
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }

}
