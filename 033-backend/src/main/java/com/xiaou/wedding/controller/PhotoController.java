package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Photo;
import com.xiaou.wedding.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/list/{orderId}")
    public Result<List<Photo>> list(@PathVariable Long orderId) {
        return Result.success(photoService.listByOrder(orderId));
    }

    @PostMapping
    public Result<Void> upload(@RequestBody Photo photo) {
        photoService.upload(photo);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Photo photo) {
        photoService.update(photo);
        return Result.success();
    }
}
