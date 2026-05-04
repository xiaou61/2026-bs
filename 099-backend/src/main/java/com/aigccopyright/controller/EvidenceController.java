package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.EvidenceRecord;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.EvidenceRecordService;
import com.aigccopyright.service.OperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceRecordService evidenceRecordService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<EvidenceRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             Long registerId,
                                             Integer evidenceStatus) {
        return Result.success(evidenceRecordService.page(pageNum, pageSize, registerId, evidenceStatus));
    }

    @PostMapping("/generate/{registerId}")
    public Result<EvidenceRecord> generate(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long registerId) {
        authService.assertCreator(role);
        EvidenceRecord record = evidenceRecordService.generate(registerId);
        operationLogService.record(userId, "版权存证", "生成", "生成存证：" + registerId);
        return Result.success(record);
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvidenceRecord entity) {
        authService.assertCreator(role);
        evidenceRecordService.saveEntity(entity);
        operationLogService.record(userId, "版权存证", "新增", "新增存证：" + entity.getEvidenceNo());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvidenceRecord entity) {
        authService.assertCreator(role);
        evidenceRecordService.saveEntity(entity);
        operationLogService.record(userId, "版权存证", "编辑", "编辑存证：" + entity.getEvidenceNo());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        evidenceRecordService.removeById(id);
        operationLogService.record(userId, "版权存证", "删除", "删除存证：" + id);
        return Result.success();
    }
}
