package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.SystemNotice;
import com.adoption.service.AuthService;
import com.adoption.service.NoticeService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(noticeService.page(pageNum, pageSize));
    }

    @GetMapping("/public/list")
    public Result<List<SystemNotice>> publicList() {
        return Result.success(noticeService.publicList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("role") String role, @RequestBody SystemNotice notice) {
        authService.assertStaff(role);
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestAttribute("role") String role, @RequestBody SystemNotice notice) {
        authService.assertStaff(role);
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        noticeService.delete(id);
        return Result.success();
    }
}
