package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.Question;
import com.gongkao.service.QuestionService;
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

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public Result<Page<Question>> list(@RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) Long bankId,
                                       @RequestParam(required = false) Long subjectId,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) String stem,
                                       @RequestParam(required = false) Integer status) {
        return Result.success(questionService.getList(pageNum, pageSize, bankId, subjectId, type, stem, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Question question) {
        questionService.add(question);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Question question) {
        questionService.update(question);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        questionService.delete(id);
        return Result.success();
    }
}
