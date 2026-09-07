package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.QuestionBank;
import com.gongkao.service.QuestionBankService;
import com.gongkao.utils.AuthUtils;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bank")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @GetMapping("/list")
    public Result<Page<QuestionBank>> list(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Long subjectId,
                                           @RequestParam(required = false) String type,
                                           @RequestParam(required = false) Integer status,
                                           HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        return Result.success(questionBankService.getList(pageNum, pageSize, name, subjectId, type, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody QuestionBank questionBank, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        questionBankService.add(questionBank);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody QuestionBank questionBank, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        questionBankService.update(questionBank);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        questionBankService.delete(id);
        return Result.success();
    }
}
