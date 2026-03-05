package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.AlumniInfo;
import com.alumni.service.AlumniService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {

    @Autowired
    private AlumniService alumniService;

    @GetMapping("/list")
    public Result<Page<AlumniInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         String name, Long gradeId, Long classId, String company, String city) {
        return Result.success(alumniService.list(pageNum, pageSize, name, gradeId, classId, company, city));
    }

    @GetMapping("/{id}")
    public Result<AlumniInfo> getById(@PathVariable Long id) {
        return Result.success(alumniService.getById(id));
    }

    @GetMapping("/my")
    public Result<AlumniInfo> getMy(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(alumniService.getByUserId(userId));
    }

    @PutMapping
    public Result<?> update(@RequestBody AlumniInfo info, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        info.setUserId(userId);
        alumniService.saveOrUpdate(info);
        return Result.success();
    }

    @GetMapping("/contacts")
    public Result<List<AlumniInfo>> contacts(Long gradeId, Long classId) {
        return Result.success(alumniService.contacts(gradeId, classId));
    }

    @GetMapping("/distribution")
    public Result<Map<String, Object>> distribution() {
        return Result.success(alumniService.distribution());
    }
}
