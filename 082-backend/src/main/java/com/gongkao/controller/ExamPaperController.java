package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.ExamPaper;
import com.gongkao.service.ExamPaperService;
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
@RequestMapping("/api/paper")
public class ExamPaperController {

    @Autowired
    private ExamPaperService examPaperService;

    @GetMapping("/list")
    public Result<Page<ExamPaper>> list(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) Long subjectId,
                                        @RequestParam(required = false) Integer publishStatus) {
        return Result.success(examPaperService.getList(pageNum, pageSize, title, subjectId, publishStatus));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ExamPaper examPaper) {
        examPaperService.add(examPaper);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ExamPaper examPaper) {
        examPaperService.update(examPaper);
        return Result.success();
    }

    @PutMapping("/publish")
    public Result<String> publish(@RequestParam Long id, @RequestParam Integer status) {
        examPaperService.publish(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        examPaperService.delete(id);
        return Result.success();
    }
}
