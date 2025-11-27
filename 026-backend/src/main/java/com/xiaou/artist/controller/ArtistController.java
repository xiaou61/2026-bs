package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.Artist;
import com.xiaou.artist.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    
    @Autowired
    private ArtistService artistService;
    
    @PostMapping("/apply")
    public Result<Artist> applyArtist(@RequestBody Artist artist) {
        Artist newArtist = artistService.applyArtist(artist);
        return Result.success(newArtist);
    }
    
    @GetMapping("/pending")
    public Result<List<Artist>> getPendingArtists() {
        List<Artist> artists = artistService.getPendingArtists();
        return Result.success(artists);
    }
    
    @PutMapping("/approve/{id}")
    public Result<String> approveArtist(@PathVariable Long id) {
        boolean success = artistService.approveArtist(id);
        return success ? Result.success("审核通过") : Result.error("审核失败");
    }
    
    @PutMapping("/reject/{id}")
    public Result<String> rejectArtist(@PathVariable Long id) {
        boolean success = artistService.rejectArtist(id);
        return success ? Result.success("已拒绝") : Result.error("操作失败");
    }
    
    @GetMapping("/{id}")
    public Result<Artist> getArtistById(@PathVariable Long id) {
        Artist artist = artistService.getArtistById(id);
        return Result.success(artist);
    }
    
    @GetMapping("/user/{userId}")
    public Result<Artist> getArtistByUserId(@PathVariable Long userId) {
        Artist artist = artistService.getArtistByUserId(userId);
        return Result.success(artist);
    }
    
    @GetMapping("/list")
    public Result<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return Result.success(artists);
    }
    
    @GetMapping("/approved")
    public Result<List<Artist>> getApprovedArtists() {
        List<Artist> artists = artistService.getApprovedArtists();
        return Result.success(artists);
    }
    
    @PutMapping("/update")
    public Result<String> updateArtist(@RequestBody Artist artist) {
        boolean success = artistService.updateArtist(artist);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }
}
