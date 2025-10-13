package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Team;
import com.xiaou.collabboard.entity.TeamMember;
import com.xiaou.collabboard.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService extends ServiceImpl<TeamMapper, Team> {

    @Autowired
    private TeamMemberService teamMemberService;

    public Team createTeam(Long userId, String teamName, String description) {
        Team team = new Team();
        team.setTeamName(teamName);
        team.setDescription(description);
        team.setCreatorId(userId);
        team.setMemberCount(1);
        team.setDocCount(0);
        team.setStatus(1);
        team.setCreateTime(LocalDateTime.now());
        team.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(team);

        teamMemberService.addMember(team.getId(), userId, "ADMIN");

        return team;
    }

    public List<Team> getUserTeams(Long userId) {
        List<TeamMember> memberList = teamMemberService.getUserTeams(userId);
        List<Long> teamIds = memberList.stream().map(TeamMember::getTeamId).toList();
        
        if (teamIds.isEmpty()) {
            return List.of();
        }

        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Team::getId, teamIds)
                .eq(Team::getStatus, 1)
                .orderByDesc(Team::getUpdateTime);
        
        return baseMapper.selectList(wrapper);
    }

    public void updateTeam(Long teamId, String teamName, String description, String avatar) {
        Team team = baseMapper.selectById(teamId);
        if (team == null) {
            throw new RuntimeException("团队不存在");
        }

        if (teamName != null) {
            team.setTeamName(teamName);
        }
        if (description != null) {
            team.setDescription(description);
        }
        if (avatar != null) {
            team.setAvatar(avatar);
        }
        team.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(team);
    }

    public void deleteTeam(Long teamId, Long userId) {
        Team team = baseMapper.selectById(teamId);
        if (team == null || !team.getCreatorId().equals(userId)) {
            throw new RuntimeException("无权限删除此团队");
        }

        team.setStatus(0);
        baseMapper.updateById(team);
    }

    public void leaveTeam(Long teamId, Long userId) {
        teamMemberService.removeMember(teamId, userId);
        
        Team team = baseMapper.selectById(teamId);
        if (team != null && team.getMemberCount() > 0) {
            team.setMemberCount(team.getMemberCount() - 1);
            baseMapper.updateById(team);
        }
    }

    public void incrementMemberCount(Long teamId) {
        Team team = baseMapper.selectById(teamId);
        if (team != null) {
            team.setMemberCount(team.getMemberCount() + 1);
            baseMapper.updateById(team);
        }
    }

    public void incrementDocCount(Long teamId) {
        Team team = baseMapper.selectById(teamId);
        if (team != null) {
            team.setDocCount(team.getDocCount() + 1);
            baseMapper.updateById(team);
        }
    }
}

