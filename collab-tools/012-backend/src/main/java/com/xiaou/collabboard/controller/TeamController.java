package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Team;
import com.xiaou.collabboard.entity.TeamMember;
import com.xiaou.collabboard.service.TeamMemberService;
import com.xiaou.collabboard.service.TeamService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/create")
    public Result<Team> createTeam(@RequestBody Map<String, String> params) {
        Long userId = UserHolder.get();
        String teamName = params.get("teamName");
        String description = params.get("description");

        Team team = teamService.createTeam(userId, teamName, description);
        return Result.success(team);
    }

    @GetMapping("/{id}")
    public Result<Team> getTeam(@PathVariable Long id) {
        Team team = teamService.getById(id);
        return Result.success(team);
    }

    @PutMapping("/{id}")
    public Result<Void> updateTeam(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String teamName = params.get("teamName");
        String description = params.get("description");
        String avatar = params.get("avatar");

        teamService.updateTeam(id, teamName, description, avatar);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTeam(@PathVariable Long id) {
        Long userId = UserHolder.get();
        teamService.deleteTeam(id, userId);
        return Result.success();
    }

    @PostMapping("/{id}/leave")
    public Result<Void> leaveTeam(@PathVariable Long id) {
        Long userId = UserHolder.get();
        teamService.leaveTeam(id, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Team>> getTeamList() {
        Long userId = UserHolder.get();
        List<Team> teams = teamService.getUserTeams(userId);
        return Result.success(teams);
    }

    @PostMapping("/{id}/invite")
    public Result<Void> inviteMember(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long userId = Long.parseLong(params.get("userId").toString());
        String role = (String) params.getOrDefault("role", "MEMBER");

        teamMemberService.addMember(id, userId, role);
        teamService.incrementMemberCount(id);

        return Result.success();
    }

    @GetMapping("/{id}/members")
    public Result<List<TeamMember>> getTeamMembers(@PathVariable Long id) {
        List<TeamMember> members = teamMemberService.getTeamMembers(id);
        return Result.success(members);
    }

    @DeleteMapping("/{teamId}/member/{userId}")
    public Result<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamMemberService.removeMember(teamId, userId);
        return Result.success();
    }

    @PutMapping("/{teamId}/member/{userId}/role")
    public Result<Void> updateMemberRole(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            @RequestBody Map<String, String> params) {

        String role = params.get("role");
        teamMemberService.updateMemberRole(teamId, userId, role);
        return Result.success();
    }
}

