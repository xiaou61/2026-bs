package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Parking;
import com.xiaou.community.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Parking parking) {
        parkingService.add(parking);
        return Result.success("Added successfully");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Parking parking) {
        parkingService.update(parking);
        return Result.success("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        parkingService.delete(id);
        return Result.success("Deleted successfully");
    }

    @GetMapping("/{id}")
    public Result<Parking> getById(@PathVariable Integer id) {
        return Result.success(parkingService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<Parking>> list() {
        return Result.success(parkingService.getAll());
    }
}
