package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.FoundItem;
import com.xiaou.entity.User;
import com.xiaou.service.FavoriteService;
import com.xiaou.service.FoundItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/found")
public class FoundItemController {

    @Autowired
    private FoundItemService foundItemService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getFoundItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            HttpSession session) {
        Map<String, Object> result = foundItemService.getFoundItems(keyword, categoryId, status, location, page, pageSize);
        
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<FoundItem> items = (List<FoundItem>) result.get("items");
            for (FoundItem item : items) {
                item.setIsFavorite(favoriteService.isFavorite(user.getId(), "found", item.getId()));
            }
        }
        
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<FoundItem> getFoundItem(@PathVariable Long id, HttpSession session) {
        FoundItem item = foundItemService.getFoundItemById(id);
        
        User user = (User) session.getAttribute("user");
        if (user != null && item != null) {
            item.setIsFavorite(favoriteService.isFavorite(user.getId(), "found", item.getId()));
        }
        
        return Result.success(item);
    }

    @GetMapping("/my")
    public Result<List<FoundItem>> getMyFoundItems(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<FoundItem> items = foundItemService.getUserFoundItems(user.getId());
        return Result.success(items);
    }

    @PostMapping
    public Result<?> addFoundItem(@RequestBody FoundItem foundItem, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            foundItem.setUserId(user.getId());
            foundItemService.addFoundItem(foundItem, foundItem.getImages() != null ? 
                foundItem.getImages().stream().map(img -> img.getImageUrl()).toList() : null);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateFoundItem(@PathVariable Long id, @RequestBody FoundItem foundItem, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            foundItem.setId(id);
            foundItem.setUserId(user.getId());
            foundItemService.updateFoundItem(foundItem, foundItem.getImages() != null ? 
                foundItem.getImages().stream().map(img -> img.getImageUrl()).toList() : null);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteFoundItem(@PathVariable Long id) {
        try {
            foundItemService.deleteFoundItem(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            foundItemService.updateStatus(id, status);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

