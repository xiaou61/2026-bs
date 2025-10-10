package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.FriendGroup;
import com.xiaou.mapper.FriendGroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendGroupService extends ServiceImpl<FriendGroupMapper, FriendGroup> {
    
    public List<FriendGroup> getGroupsByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<FriendGroup>()
                .eq(FriendGroup::getUserId, userId)
                .orderByAsc(FriendGroup::getSortOrder));
    }
    
    public boolean createDefaultGroup(Long userId) {
        FriendGroup group = new FriendGroup();
        group.setUserId(userId);
        group.setGroupName("默认分组");
        group.setSortOrder(0);
        return this.save(group);
    }
}

