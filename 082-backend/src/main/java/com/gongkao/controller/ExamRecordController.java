package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.ExamRecord;
import com.gongkao.service.ExamRecordService;
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
@RequestMapping("/api/exam-record")
public class ExamRecordController {

    @Autowired
    private ExamRecordService examRecordService;

    @GetMapping("/list")
    public Result<Page<ExamRecord>> list(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) Long paperId,
                                         @RequestParam(required = false) String status,
                                         @RequestParam(required = false) Integer passStatus) {
        return Result.success(examRecordService.getList(pageNum, pageSize, userId, paperId, status, passStatus));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ExamRecord examRecord) {
        examRecordService.add(examRecord);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ExamRecord examRecord) {
        examRecordService.update(examRecord);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        examRecordService.delete(id);
        return Result.success();
    }
}
