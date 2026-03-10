package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.FollowUpRecord;
import com.eldercare.service.FollowUpService;
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

@RestController
@RequestMapping("/api/follow-up")
public class FollowUpController {

    @Autowired
    private FollowUpService followUpService;

    @GetMapping("/list")
    public Result<Page<FollowUpRecord>> list(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(required = false) Long elderId,
                                             @RequestParam(required = false) Integer status) {
        return Result.success(followUpService.page(pageNum, pageSize, elderId, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody FollowUpRecord followUpRecord,
                              @RequestAttribute("userId") String userId) {
        followUpService.add(followUpRecord, Long.valueOf(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody FollowUpRecord followUpRecord,
                                 @RequestAttribute("userId") String userId) {
        followUpService.update(followUpRecord, Long.valueOf(userId));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        followUpService.delete(id);
        return Result.success();
    }
}
