package com.teachres.controller;

import com.teachres.common.Result;
import com.teachres.entity.MaterialAudit;
import com.teachres.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/list")
    public Result<List<MaterialAudit>> list(@RequestParam(required = false) Long materialId) {
        return Result.success(materialService.auditList(materialId));
    }

    @PutMapping("/submit")
    public Result<String> submit(@RequestBody Map<String, Object> params,
                                 @RequestAttribute("userId") Long userId) {
        Long materialId = Long.valueOf(String.valueOf(params.get("materialId")));
        Integer auditStatus = Integer.valueOf(String.valueOf(params.get("auditStatus")));
        String auditRemark = params.get("auditRemark") == null ? "" : String.valueOf(params.get("auditRemark"));
        materialService.audit(materialId, auditStatus, auditRemark, userId);
        return Result.success();
    }
}
