package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Photographer;
import com.xiaou.wedding.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photographer")
public class PhotographerController {

    @Autowired
    private PhotographerService photographerService;

    @GetMapping("/list")
    public Result<List<Photographer>> list(@RequestParam(required = false) String level) {
        return Result.success(photographerService.list(level));
    }

    @GetMapping("/{id}")
    public Result<Photographer> detail(@PathVariable Long id) {
        return Result.success(photographerService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Photographer photographer) {
        photographerService.create(photographer);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Photographer photographer) {
        photographerService.update(photographer);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        photographerService.delete(id);
        return Result.success();
    }
}
