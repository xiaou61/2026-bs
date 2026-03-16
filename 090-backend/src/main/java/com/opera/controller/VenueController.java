package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.VenueInfo;
import com.opera.service.VenueService;
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
@RequestMapping("/api/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/list")
    public Result<PageInfo<VenueInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Long majorId,
                                            @RequestParam(required = false) Integer status) {
        return Result.success(venueService.list(name, majorId, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<VenueInfo>> enabled() {
        return Result.success(venueService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody VenueInfo entity, @RequestAttribute("role") String role) {
        venueService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody VenueInfo entity, @RequestAttribute("role") String role) {
        venueService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        venueService.delete(id, role);
        return Result.success();
    }
}


