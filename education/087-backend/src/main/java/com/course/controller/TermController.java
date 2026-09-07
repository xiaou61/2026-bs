package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.AcademicTerm;
import com.course.service.TermService;
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

@RestController
@RequestMapping("/api/term")
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping("/list")
    public Result<PageInfo<AcademicTerm>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String termName,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) Integer currentFlag) {
        return Result.success(termService.list(termName, status, currentFlag, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<AcademicTerm>> enabled() {
        return Result.success(termService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody AcademicTerm entity, @RequestAttribute("role") String role) {
        termService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody AcademicTerm entity, @RequestAttribute("role") String role) {
        termService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        termService.delete(id, role);
        return Result.success();
    }
}
