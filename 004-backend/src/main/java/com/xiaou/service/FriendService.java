package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Friend;
import com.xiaou.entity.User;
import com.xiaou.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendService extends ServiceImpl<FriendMapper, Friend> {
    
    @Autowired
    private UserService userService;
    
    public List<Map<String, Object>> getFriendList(Long userId) {
        List<Friend> friends = this.list(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getStatus, 1));
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Friend friend : friends) {
            User user = userService.getById(friend.getFriendId());
            if (user != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("friendId", user.getId());
                map.put("username", user.getUsername());
                map.put("nickname", user.getNickname());
                map.put("avatar", user.getAvatar());
                map.put("groupId", friend.getGroupId());
                map.put("remark", friend.getRemark());
                result.add(map);
            }
        }
        return result;
    }
    
    public boolean isFriend(Long userId, Long friendId) {
        return this.count(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getFriendId, friendId)
                .eq(Friend::getStatus, 1)) > 0;
    }
    
    public boolean addFriend(Long userId, Long friendId, Long groupId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setGroupId(groupId);
        friend.setStatus(1);
        return this.save(friend);
    }
    
    public boolean deleteFriend(Long userId, Long friendId) {
        return this.remove(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getFriendId, friendId));
    }
}

