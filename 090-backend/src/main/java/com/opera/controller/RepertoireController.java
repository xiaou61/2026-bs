package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.RepertoireInfo;
import com.opera.service.RepertoireService;
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
@RequestMapping("/api/repertoire")
public class RepertoireController {

    @Autowired
    private RepertoireService courseService;

    @GetMapping("/list")
    public Result<PageInfo<RepertoireInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String courseName,
                                             @RequestParam(required = false) Long artistId,
                                             @RequestParam(required = false) Long termId,
                                             @RequestParam(required = false) Integer status) {
        return Result.success(courseService.list(courseName, artistId, termId, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<RepertoireInfo>> enabled() {
        return Result.success(courseService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody RepertoireInfo entity, @RequestAttribute("role") String role) {
        courseService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody RepertoireInfo entity, @RequestAttribute("role") String role) {
        courseService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        courseService.delete(id, role);
        return Result.success();
    }
}


