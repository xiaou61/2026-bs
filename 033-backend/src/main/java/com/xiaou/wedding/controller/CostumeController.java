package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Costume;
import com.xiaou.wedding.entity.CostumeBorrow;
import com.xiaou.wedding.service.CostumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/costume")
public class CostumeController {

    @Autowired
    private CostumeService costumeService;

    @GetMapping("/list")
    public Result<List<Costume>> list(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) String status) {
        return Result.success(costumeService.list(category, status));
    }

    @GetMapping("/{id}")
    public Result<Costume> detail(@PathVariable Long id) {
        return Result.success(costumeService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Costume costume) {
        costumeService.create(costume);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Costume costume) {
        costumeService.update(costume);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        costumeService.delete(id);
        return Result.success();
    }

    @PostMapping("/borrow")
    public Result<Void> borrow(@RequestBody CostumeBorrow borrow) {
        costumeService.borrow(borrow);
        return Result.success();
    }

    @PostMapping("/borrow/{id}/return")
    public Result<Void> returnCostume(@PathVariable Long id) {
        costumeService.returnCostume(id);
        return Result.success();
    }

    @GetMapping("/borrow/list")
    public Result<List<CostumeBorrow>> borrowList(@RequestParam(required = false) Long costumeId,
                                                  @RequestParam(required = false) String status) {
        return Result.success(costumeService.borrowList(costumeId, status));
    }
}
