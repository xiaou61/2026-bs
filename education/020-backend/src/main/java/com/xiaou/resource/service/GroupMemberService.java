package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.GroupMember;
import com.xiaou.resource.mapper.GroupMemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService extends ServiceImpl<GroupMemberMapper, GroupMember> {

    public List<GroupMember> getGroupMembers(Long groupId) {
        QueryWrapper<GroupMember> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        return this.list(wrapper);
    }
}

