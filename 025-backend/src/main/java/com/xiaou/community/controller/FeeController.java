package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Fee;
import com.xiaou.community.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @PostMapping("/create")
    public Result<String> create(@RequestBody Fee fee) {
        feeService.create(fee);
        return Result.success("Created successfully");
    }

    @PostMapping("/pay/{id}")
    public Result<String> pay(@PathVariable Integer id) {
        feeService.pay(id);
        return Result.success("Paid successfully");
    }

    @GetMapping("/owner/{ownerId}")
    public Result<List<Fee>> getByOwnerId(@PathVariable Integer ownerId) {
        return Result.success(feeService.getByOwnerId(ownerId));
    }

    @GetMapping("/list")
    public Result<List<Fee>> list() {
        return Result.success(feeService.getAll());
    }
}
