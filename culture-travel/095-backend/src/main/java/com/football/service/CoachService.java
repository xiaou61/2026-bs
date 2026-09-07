package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.CoachInfo;
import com.football.entity.TeamInfo;
import com.football.mapper.CoachInfoMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoachService {

    @Resource
    private CoachInfoMapper coachInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    public PageResult<CoachInfo> page(Integer pageNum, Integer pageSize, Long teamId, String name, Integer status) {
        Page<CoachInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CoachInfo> wrapper = new LambdaQueryWrapper<CoachInfo>()
                .eq(teamId != null, CoachInfo::getTeamId, teamId)
                .like(StringUtils.hasText(name), CoachInfo::getName, name == null ? null : name.trim())
                .eq(status != null, CoachInfo::getStatus, status)
                .orderByDesc(CoachInfo::getId);
        Page<CoachInfo> resultPage = coachInfoMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<CoachInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(CoachInfo info) {
        if (info == null || info.getTeamId() == null || !StringUtils.hasText(info.getName())) {
            throw new BusinessException("教练信息不完整");
        }
        if (teamInfoMapper.selectById(info.getTeamId()) == null) {
            throw new BusinessException("球队不存在");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getCoachNo())) {
                info.setCoachNo("H" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            coachInfoMapper.insert(info);
            return;
        }
        CoachInfo db = coachInfoMapper.selectById(info.getId());
        if (db == null) {
            throw new BusinessException("教练不存在");
        }
        db.setTeamId(info.getTeamId());
        db.setName(info.getName().trim());
        db.setNationality(StringUtils.hasText(info.getNationality()) ? info.getNationality().trim() : null);
        db.setAge(info.getAge());
        db.setFormation(StringUtils.hasText(info.getFormation()) ? info.getFormation().trim() : null);
        db.setTenureStart(info.getTenureStart());
        db.setPhone(StringUtils.hasText(info.getPhone()) ? info.getPhone().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        coachInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        coachInfoMapper.deleteById(id);
    }

    private void fillDetails(List<CoachInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                        .in(TeamInfo::getId, list.stream().map(CoachInfo::getTeamId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setTeamName(teamMap.get(item.getTeamId())));
    }
}
