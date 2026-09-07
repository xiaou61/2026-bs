package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.BusinessException;
import com.gongkao.common.Result;
import com.gongkao.entity.ExamRecord;
import com.gongkao.service.ExamRecordService;
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
                                         @RequestParam(required = false) Integer passStatus,
                                         HttpServletRequest request) {
        Long currentUserId = AuthUtils.getUserId(request);
        if (AuthUtils.isStudent(request)) {
            userId = currentUserId;
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        return Result.success(examRecordService.getList(pageNum, pageSize, userId, paperId, status, passStatus));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ExamRecord examRecord, HttpServletRequest request) {
        Long currentUserId = AuthUtils.getUserId(request);
        if (AuthUtils.isStudent(request)) {
            examRecord.setUserId(currentUserId);
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        examRecordService.add(examRecord);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ExamRecord examRecord, HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            ExamRecord current = examRecordService.getById(examRecord.getId());
            if (!AuthUtils.getUserId(request).equals(current.getUserId())) {
                throw new BusinessException(403, "无权操作他人考试记录");
            }
            examRecord.setUserId(current.getUserId());
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        examRecordService.update(examRecord);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        if (AuthUtils.isStudent(request)) {
            ExamRecord current = examRecordService.getById(id);
            if (!AuthUtils.getUserId(request).equals(current.getUserId())) {
                throw new BusinessException(403, "无权操作他人考试记录");
            }
        } else {
            AuthUtils.requireAdminOrTeacher(request);
        }
        examRecordService.delete(id);
        return Result.success();
    }
}
