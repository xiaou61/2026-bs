package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.LostItem;
import com.xiaou.entity.User;
import com.xiaou.service.FavoriteService;
import com.xiaou.service.LostItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lost")
public class LostItemController {

    @Autowired
    private LostItemService lostItemService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getLostItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            HttpSession session) {
        Map<String, Object> result = lostItemService.getLostItems(keyword, categoryId, status, location, page, pageSize);
        
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<LostItem> items = (List<LostItem>) result.get("items");
            for (LostItem item : items) {
                item.setIsFavorite(favoriteService.isFavorite(user.getId(), "lost", item.getId()));
            }
        }
        
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<LostItem> getLostItem(@PathVariable Long id, HttpSession session) {
        LostItem item = lostItemService.getLostItemById(id);
        
        User user = (User) session.getAttribute("user");
        if (user != null && item != null) {
            item.setIsFavorite(favoriteService.isFavorite(user.getId(), "lost", item.getId()));
        }
        
        return Result.success(item);
    }

    @GetMapping("/my")
    public Result<List<LostItem>> getMyLostItems(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<LostItem> items = lostItemService.getUserLostItems(user.getId());
        return Result.success(items);
    }

    @PostMapping
    public Result<?> addLostItem(@RequestBody LostItem lostItem, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            lostItem.setUserId(user.getId());
            lostItemService.addLostItem(lostItem, lostItem.getImages() != null ? 
                lostItem.getImages().stream().map(img -> img.getImageUrl()).toList() : null);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateLostItem(@PathVariable Long id, @RequestBody LostItem lostItem, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            lostItem.setId(id);
            lostItem.setUserId(user.getId());
            lostItemService.updateLostItem(lostItem, lostItem.getImages() != null ? 
                lostItem.getImages().stream().map(img -> img.getImageUrl()).toList() : null);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteLostItem(@PathVariable Long id) {
        try {
            lostItemService.deleteLostItem(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            lostItemService.updateStatus(id, status);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

