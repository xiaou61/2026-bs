package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.InfringementClue;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.InfringementClueService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/clue")
public class ClueController {

    @Autowired
    private InfringementClueService infringementClueService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<InfringementClue>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               Long assetId,
                                               Integer status,
                                               String keyword) {
        return Result.success(infringementClueService.page(pageNum, pageSize, assetId, status, keyword));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InfringementClue entity) {
        authService.assertCreator(role);
        infringementClueService.saveEntity(entity, userId);
        operationLogService.record(userId, "侵权线索", "新增", "新增线索：" + entity.getPlatformName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InfringementClue entity) {
        authService.assertCreator(role);
        infringementClueService.saveEntity(entity, userId);
        operationLogService.record(userId, "侵权线索", "编辑", "编辑线索：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/handle")
    public Result<Void> handle(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> body) {
        authService.assertAuditor(role);
        Long id = Long.valueOf(String.valueOf(body.get("id")));
        Integer status = body.get("status") == null ? 1 : Integer.valueOf(String.valueOf(body.get("status")));
        String comment = body.get("handleComment") == null ? "" : String.valueOf(body.get("handleComment"));
        infringementClueService.handle(id, userId, status, comment);
        operationLogService.record(userId, "侵权线索", "处理", "处理线索：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        infringementClueService.removeById(id);
        operationLogService.record(userId, "侵权线索", "删除", "删除线索：" + id);
        return Result.success();
    }
}
