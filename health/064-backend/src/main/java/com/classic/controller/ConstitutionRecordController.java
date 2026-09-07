package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.entity.ConstitutionRecord;
import com.classic.service.ConstitutionRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/constitution")
public class ConstitutionRecordController {

    @Resource
    private ConstitutionRecordService constitutionRecordService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            HttpServletRequest request) {
        return Result.success(constitutionRecordService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, status));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long userId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(constitutionRecordService.page(pageNum, pageSize, userId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody ConstitutionRecord record, HttpServletRequest request) {
        checkUser((String) request.getAttribute("role"));
        constitutionRecordService.save(record, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<?> reply(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("suggestion") == null) {
            throw new BusinessException("参数不完整");
        }
        constitutionRecordService.reply(((Number) params.get("id")).longValue(), params.get("suggestion").toString());
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }

    private void checkUser(String role) {
        if (!"USER".equals(role)) {
            throw new BusinessException("仅用户可提交");
        }
    }
}
