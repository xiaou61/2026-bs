package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.CookingRecordDTO;
import com.xiaou.service.CookingRecordService;
import com.xiaou.vo.CookingRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cooking")
public class CookingController {

    @Autowired
    private CookingRecordService cookingRecordService;

    @PostMapping("/record")
    public Result<String> createRecord(@RequestAttribute("userId") Long userId,
                                       @RequestBody CookingRecordDTO dto) {
        cookingRecordService.createRecord(userId, dto);
        return Result.success("打卡成功");
    }

    @GetMapping("/record/list")
    public Result<List<CookingRecordVO>> getRecordList(@RequestAttribute("userId") Long userId) {
        List<CookingRecordVO> list = cookingRecordService.getRecordList(userId);
        return Result.success(list);
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(@RequestAttribute("userId") Long userId) {
        Map<String, Object> stats = cookingRecordService.getStats(userId);
        return Result.success(stats);
    }
}

