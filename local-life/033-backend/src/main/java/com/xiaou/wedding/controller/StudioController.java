package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Studio;
import com.xiaou.wedding.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @GetMapping("/list")
    public Result<List<Studio>> list() {
        return Result.success(studioService.list());
    }

    @GetMapping("/{id}")
    public Result<Studio> detail(@PathVariable Long id) {
        return Result.success(studioService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Studio studio) {
        studioService.create(studio);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Studio studio) {
        studioService.update(studio);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studioService.delete(id);
        return Result.success();
    }
}
