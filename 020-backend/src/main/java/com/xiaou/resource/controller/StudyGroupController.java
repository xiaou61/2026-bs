package com.xiaou.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.GroupMember;
import com.xiaou.resource.entity.StudyGroup;
import com.xiaou.resource.service.GroupMemberService;
import com.xiaou.resource.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/group")
public class StudyGroupController {

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping("/create")
    public Result<String> createGroup(@RequestAttribute Long userId, @RequestBody StudyGroup group) {
        boolean success = studyGroupService.createGroup(group, userId);
        return success ? Result.success("创建成功") : Result.error("创建失败");
    }

    @GetMapping("/list")
    public Result<IPage<StudyGroup>> getGroupList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        IPage<StudyGroup> pageData = studyGroupService.getGroupList(page, size, category, keyword);
        return Result.success(pageData);
    }

    @GetMapping("/{id}")
    public Result<StudyGroup> getGroupDetail(@PathVariable Long id) {
        StudyGroup group = studyGroupService.getGroupDetail(id);
        return Result.success(group);
    }

    @PostMapping("/join")
    public Result<String> joinGroup(@RequestAttribute Long userId, @RequestBody Map<String, Long> params) {
        Long groupId = params.get("groupId");
        boolean success = studyGroupService.joinGroup(groupId, userId);
        return success ? Result.success("加入成功") : Result.error("加入失败");
    }

    @PostMapping("/leave")
    public Result<String> leaveGroup(@RequestAttribute Long userId, @RequestBody Map<String, Long> params) {
        Long groupId = params.get("groupId");
        boolean success = studyGroupService.leaveGroup(groupId, userId);
        return success ? Result.success("退出成功") : Result.error("退出失败");
    }

    @GetMapping("/members/{groupId}")
    public Result<List<GroupMember>> getGroupMembers(@PathVariable Long groupId) {
        List<GroupMember> members = groupMemberService.getGroupMembers(groupId);
        return Result.success(members);
    }

    @GetMapping("/my")
    public Result<IPage<StudyGroup>> getMyGroups(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<StudyGroup> pageData = studyGroupService.getMyGroups(userId, page, size);
        return Result.success(pageData);
    }
}

