package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.SponsorRelation;
import com.charity.service.SponsorRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorRelationController {

    @Autowired
    private SponsorRelationService sponsorRelationService;

    @GetMapping("/list")
    public Result<Page<SponsorRelation>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(required = false) Long childId,
                                                 @RequestParam(required = false) Long donorId) {
        Page<SponsorRelation> page = sponsorRelationService.getList(pageNum, pageSize, childId, donorId);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody SponsorRelation sponsorRelation) {
        sponsorRelationService.add(sponsorRelation);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SponsorRelation sponsorRelation) {
        sponsorRelationService.update(sponsorRelation);
        return Result.success();
    }
}
