package com.football.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.FanFollow;
import com.football.entity.TeamInfo;
import com.football.entity.User;
import com.football.mapper.FanFollowMapper;
import com.football.mapper.TeamInfoMapper;
import com.football.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Resource
    private FanFollowMapper fanFollowMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<FanFollow> page(Integer pageNum, Integer pageSize, Long userId, String role) {
        Page<FanFollow> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<FanFollow> wrapper = new LambdaQueryWrapper<FanFollow>()
                .eq("FAN".equalsIgnoreCase(role), FanFollow::getUserId, userId)
                .orderByDesc(FanFollow::getId);
        Page<FanFollow> resultPage = fanFollowMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<FanFollow> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public boolean toggle(Long userId, Long teamId) {
        if (teamInfoMapper.selectById(teamId) == null) {
            throw new BusinessException("球队不存在");
        }
        FanFollow exists = fanFollowMapper.selectOne(new LambdaQueryWrapper<FanFollow>()
                .eq(FanFollow::getUserId, userId)
                .eq(FanFollow::getTeamId, teamId)
                .last("limit 1"));
        if (exists != null) {
            fanFollowMapper.deleteById(exists.getId());
            return false;
        }
        FanFollow follow = new FanFollow();
        follow.setUserId(userId);
        follow.setTeamId(teamId);
        fanFollowMapper.insert(follow);
        return true;
    }

    public Long countAll() {
        return fanFollowMapper.selectCount(new LambdaQueryWrapper<>());
    }

    private void fillDetails(List<FanFollow> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(FanFollow::getUserId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                        .in(TeamInfo::getId, list.stream().map(FanFollow::getTeamId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setUsername(userMap.get(item.getUserId()));
            item.setTeamName(teamMap.get(item.getTeamId()));
        });
    }
}
