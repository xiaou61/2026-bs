package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.TeamMember;
import com.xiaou.collabboard.mapper.TeamMemberMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamMemberService extends ServiceImpl<TeamMemberMapper, TeamMember> {

    public TeamMember addMember(Long teamId, Long userId, String role) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId);
        
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户已在团队中");
        }

        TeamMember member = new TeamMember();
        member.setTeamId(teamId);
        member.setUserId(userId);
        member.setRole(role);
        member.setJoinTime(LocalDateTime.now());
        member.setCreateTime(LocalDateTime.now());

        baseMapper.insert(member);
        return member;
    }

    public List<TeamMember> getTeamMembers(Long teamId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId)
                .orderByDesc(TeamMember::getJoinTime);
        return baseMapper.selectList(wrapper);
    }

    public List<TeamMember> getUserTeams(Long userId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getUserId, userId);
        return baseMapper.selectList(wrapper);
    }

    public void removeMember(Long teamId, Long userId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId);
        baseMapper.delete(wrapper);
    }

    public void updateMemberRole(Long teamId, Long userId, String role) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId);
        
        TeamMember member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new RuntimeException("成员不存在");
        }

        member.setRole(role);
        baseMapper.updateById(member);
    }

    public boolean isTeamAdmin(Long teamId, Long userId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId)
                .eq(TeamMember::getRole, "ADMIN");
        
        return baseMapper.selectCount(wrapper) > 0;
    }
}

