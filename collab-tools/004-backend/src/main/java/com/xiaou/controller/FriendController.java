package com.xiaou.controller;

import com.xiaou.common.CurrentUser;
import com.xiaou.common.R;
import com.xiaou.entity.FriendGroup;
import com.xiaou.service.FriendGroupService;
import com.xiaou.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    
    @Autowired
    private FriendService friendService;
    
    @Autowired
    private FriendGroupService friendGroupService;
    
    @GetMapping("/list")
    public R getFriendList() {
        Long userId = CurrentUser.get();
        List<Map<String, Object>> friends = friendService.getFriendList(userId);
        return R.ok(friends);
    }
    
    @PostMapping("/add")
    public R addFriend(@RequestBody Map<String, Object> params) {
        Long userId = CurrentUser.get();
        Long friendId = Long.parseLong(params.get("friendId").toString());
        Long groupId = params.get("groupId") != null ? Long.parseLong(params.get("groupId").toString()) : null;
        
        if (userId.equals(friendId)) {
            return R.error("不能添加自己为好友");
        }
        
        if (friendService.isFriend(userId, friendId)) {
            return R.error("已经是好友了");
        }
        
        if (groupId == null) {
            List<FriendGroup> groups = friendGroupService.getGroupsByUserId(userId);
            if (!groups.isEmpty()) {
                groupId = groups.get(0).getId();
            }
        }
        
        friendService.addFriend(userId, friendId, groupId);
        friendService.addFriend(friendId, userId, null);
        
        return R.ok("添加成功");
    }
    
    @DeleteMapping("/delete/{friendId}")
    public R deleteFriend(@PathVariable Long friendId) {
        Long userId = CurrentUser.get();
        friendService.deleteFriend(userId, friendId);
        friendService.deleteFriend(friendId, userId);
        return R.ok("删除成功");
    }
    
    @GetMapping("/group/list")
    public R getGroupList() {
        Long userId = CurrentUser.get();
        List<FriendGroup> groups = friendGroupService.getGroupsByUserId(userId);
        return R.ok(groups);
    }
    
    @PostMapping("/group/add")
    public R addGroup(@RequestBody FriendGroup group) {
        Long userId = CurrentUser.get();
        group.setUserId(userId);
        friendGroupService.save(group);
        return R.ok("创建成功");
    }
}

