package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.GroupMember;
import com.xiaou.resource.entity.StudyGroup;
import com.xiaou.resource.mapper.StudyGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudyGroupService extends ServiceImpl<StudyGroupMapper, StudyGroup> {

    @Autowired
    private GroupMemberService groupMemberService;

    public boolean createGroup(StudyGroup group, Long userId) {
        group.setCreatorId(userId);
        group.setCurrentMembers(1);
        group.setStatus("active");
        group.setCreateTime(LocalDateTime.now());
        group.setUpdateTime(LocalDateTime.now());
        boolean created = this.save(group);

        if (created) {
            GroupMember member = new GroupMember();
            member.setGroupId(group.getId());
            member.setUserId(userId);
            member.setRole("creator");
            member.setJoinTime(LocalDateTime.now());
            groupMemberService.save(member);
        }
        return created;
    }

    public IPage<StudyGroup> getGroupList(Integer page, Integer size, String category, String keyword) {
        Page<StudyGroup> pageParam = new Page<>(page, size);
        QueryWrapper<StudyGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "active");
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("name", keyword).or().like("description", keyword);
        }
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public StudyGroup getGroupDetail(Long id) {
        return this.getById(id);
    }

    public boolean joinGroup(Long groupId, Long userId) {
        StudyGroup group = this.getById(groupId);
        if (group == null || group.getCurrentMembers() >= group.getMaxMembers()) {
            return false;
        }

        QueryWrapper<GroupMember> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId).eq("user_id", userId);
        if (groupMemberService.getOne(wrapper) != null) {
            return false;
        }

        GroupMember member = new GroupMember();
        member.setGroupId(groupId);
        member.setUserId(userId);
        member.setRole("member");
        member.setJoinTime(LocalDateTime.now());
        groupMemberService.save(member);

        group.setCurrentMembers(group.getCurrentMembers() + 1);
        group.setUpdateTime(LocalDateTime.now());
        this.updateById(group);
        return true;
    }

    public boolean leaveGroup(Long groupId, Long userId) {
        QueryWrapper<GroupMember> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId).eq("user_id", userId);
        GroupMember member = groupMemberService.getOne(wrapper);
        if (member == null) {
            return false;
        }

        if ("creator".equals(member.getRole())) {
            return false;
        }

        groupMemberService.removeById(member.getId());

        StudyGroup group = this.getById(groupId);
        group.setCurrentMembers(group.getCurrentMembers() - 1);
        group.setUpdateTime(LocalDateTime.now());
        this.updateById(group);
        return true;
    }

    public IPage<StudyGroup> getMyGroups(Long userId, Integer page, Integer size) {
        Page<StudyGroup> pageParam = new Page<>(page, size);
        QueryWrapper<GroupMember> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("user_id", userId);
        var members = groupMemberService.list(memberWrapper);

        if (members.isEmpty()) {
            return new Page<>(page, size);
        }

        QueryWrapper<StudyGroup> wrapper = new QueryWrapper<>();
        wrapper.in("id", members.stream().map(GroupMember::getGroupId).toList());
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }
}

