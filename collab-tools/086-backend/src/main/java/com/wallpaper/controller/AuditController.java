package com.wallpaper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wallpaper.common.Result;
import com.wallpaper.dto.AuditDTO;
import com.wallpaper.entity.WallpaperAudit;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/list")
    public Result<IPage<WallpaperInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) Integer auditStatus,
                                             @RequestAttribute("userId") Long userId) {
        return Result.success(auditService.list(title, auditStatus, pageNum, pageSize, userId));
    }

    @PutMapping("/submit")
    public Result<String> submit(@RequestBody AuditDTO auditDTO, @RequestAttribute("userId") Long userId) {
        auditService.submit(auditDTO, userId);
        return Result.success();
    }

    @GetMapping("/record/{wallpaperId}")
    public Result<List<WallpaperAudit>> record(@PathVariable Long wallpaperId, @RequestAttribute("userId") Long userId) {
        return Result.success(auditService.recordList(wallpaperId, userId));
    }
}
