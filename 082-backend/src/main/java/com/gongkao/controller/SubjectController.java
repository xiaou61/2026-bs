package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.Subject;
import com.gongkao.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/list")
    public Result<Page<Subject>> list(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer status) {
        return Result.success(subjectService.getList(pageNum, pageSize, name, status));
    }

    @GetMapping("/public/list")
    public Result<List<Subject>> publicList() {
        return Result.success(subjectService.getPublicList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Subject subject) {
        subjectService.add(subject);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Subject subject) {
        subjectService.update(subject);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return Result.success();
    }
}
