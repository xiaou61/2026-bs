package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.PlayerInfo;
import com.football.entity.TeamInfo;
import com.football.mapper.PlayerInfoMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    public PageResult<PlayerInfo> page(Integer pageNum, Integer pageSize, Long teamId, String name, String position, Integer status) {
        Page<PlayerInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PlayerInfo> wrapper = new LambdaQueryWrapper<PlayerInfo>()
                .eq(teamId != null, PlayerInfo::getTeamId, teamId)
                .like(StringUtils.hasText(name), PlayerInfo::getName, name == null ? null : name.trim())
                .eq(StringUtils.hasText(position), PlayerInfo::getPosition, position == null ? null : position.trim())
                .eq(status != null, PlayerInfo::getStatus, status)
                .orderByDesc(PlayerInfo::getGoalCount)
                .orderByAsc(PlayerInfo::getJerseyNumber);
        Page<PlayerInfo> resultPage = playerInfoMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<PlayerInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<PlayerInfo> publicList() {
        List<PlayerInfo> list = playerInfoMapper.selectList(new LambdaQueryWrapper<PlayerInfo>()
                .eq(PlayerInfo::getStatus, 1)
                .orderByDesc(PlayerInfo::getGoalCount)
                .last("limit 20"));
        fillDetails(list);
        return list;
    }

    public List<PlayerInfo> topScorers() {
        List<PlayerInfo> list = playerInfoMapper.selectList(new LambdaQueryWrapper<PlayerInfo>()
                .eq(PlayerInfo::getStatus, 1)
                .orderByDesc(PlayerInfo::getGoalCount)
                .orderByDesc(PlayerInfo::getAssistCount)
                .last("limit 10"));
        fillDetails(list);
        return list;
    }

    public void save(PlayerInfo info) {
        if (info == null || info.getTeamId() == null || !StringUtils.hasText(info.getName())) {
            throw new BusinessException("球员信息不完整");
        }
        if (teamInfoMapper.selectById(info.getTeamId()) == null) {
            throw new BusinessException("球队不存在");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getPlayerNo())) {
                info.setPlayerNo("P" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            info.setGoalCount(info.getGoalCount() == null ? 0 : info.getGoalCount());
            info.setAssistCount(info.getAssistCount() == null ? 0 : info.getAssistCount());
            playerInfoMapper.insert(info);
            return;
        }
        PlayerInfo db = playerInfoMapper.selectById(info.getId());
        if (db == null) {
            throw new BusinessException("球员不存在");
        }
        db.setTeamId(info.getTeamId());
        db.setName(info.getName().trim());
        db.setJerseyNumber(info.getJerseyNumber());
        db.setPosition(StringUtils.hasText(info.getPosition()) ? info.getPosition().trim() : null);
        db.setAge(info.getAge());
        db.setNationality(StringUtils.hasText(info.getNationality()) ? info.getNationality().trim() : null);
        db.setGoalCount(info.getGoalCount() == null ? 0 : info.getGoalCount());
        db.setAssistCount(info.getAssistCount() == null ? 0 : info.getAssistCount());
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        playerInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        playerInfoMapper.deleteById(id);
    }

    private void fillDetails(List<PlayerInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                        .in(TeamInfo::getId, list.stream().map(PlayerInfo::getTeamId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setTeamName(teamMap.get(item.getTeamId())));
    }
}
