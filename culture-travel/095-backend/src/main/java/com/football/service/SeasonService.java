package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.LeagueInfo;
import com.football.entity.SeasonInfo;
import com.football.mapper.LeagueInfoMapper;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.SeasonInfoMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    @Resource
    private LeagueInfoMapper leagueInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    public PageResult<SeasonInfo> page(Integer pageNum, Integer pageSize, Long leagueId, String seasonName, Integer status) {
        Page<SeasonInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SeasonInfo> wrapper = new LambdaQueryWrapper<SeasonInfo>()
                .eq(leagueId != null, SeasonInfo::getLeagueId, leagueId)
                .like(StringUtils.hasText(seasonName), SeasonInfo::getSeasonName, seasonName == null ? null : seasonName.trim())
                .eq(status != null, SeasonInfo::getStatus, status)
                .orderByDesc(SeasonInfo::getId);
        Page<SeasonInfo> resultPage = seasonInfoMapper.selectPage(page, wrapper);
        fillLeagueNames(resultPage.getRecords());
        PageResult<SeasonInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<SeasonInfo> listAll(Long leagueId) {
        List<SeasonInfo> list = seasonInfoMapper.selectList(new LambdaQueryWrapper<SeasonInfo>()
                .eq(leagueId != null, SeasonInfo::getLeagueId, leagueId)
                .eq(SeasonInfo::getStatus, 1)
                .orderByDesc(SeasonInfo::getId));
        fillLeagueNames(list);
        return list;
    }

    public SeasonInfo requireById(Long id) {
        SeasonInfo info = seasonInfoMapper.selectById(id);
        if (info == null) {
            throw new BusinessException("赛季不存在");
        }
        return info;
    }

    public void save(SeasonInfo info) {
        if (info == null || info.getLeagueId() == null || !StringUtils.hasText(info.getSeasonName())) {
            throw new BusinessException("赛季信息不完整");
        }
        if (leagueInfoMapper.selectById(info.getLeagueId()) == null) {
            throw new BusinessException("联赛不存在");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getSeasonNo())) {
                info.setSeasonNo("S" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            seasonInfoMapper.insert(info);
            return;
        }
        SeasonInfo db = requireById(info.getId());
        db.setLeagueId(info.getLeagueId());
        db.setSeasonName(info.getSeasonName().trim());
        db.setYearLabel(StringUtils.hasText(info.getYearLabel()) ? info.getYearLabel().trim() : null);
        db.setStartDate(info.getStartDate());
        db.setEndDate(info.getEndDate());
        db.setRounds(info.getRounds());
        db.setRemark(StringUtils.hasText(info.getRemark()) ? info.getRemark().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        seasonInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (teamInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.TeamInfo>().eq(com.football.entity.TeamInfo::getSeasonId, id)) > 0) {
            throw new BusinessException("该赛季下存在球队，无法删除");
        }
        if (matchScheduleMapper.selectCount(new LambdaQueryWrapper<com.football.entity.MatchSchedule>().eq(com.football.entity.MatchSchedule::getSeasonId, id)) > 0) {
            throw new BusinessException("该赛季下存在比赛，无法删除");
        }
        seasonInfoMapper.deleteById(id);
    }

    public Long countActive() {
        return seasonInfoMapper.selectCount(new LambdaQueryWrapper<SeasonInfo>().eq(SeasonInfo::getStatus, 1));
    }

    public Long getDefaultSeasonId() {
        SeasonInfo info = seasonInfoMapper.selectOne(new LambdaQueryWrapper<SeasonInfo>()
                .eq(SeasonInfo::getStatus, 1)
                .orderByDesc(SeasonInfo::getId)
                .last("limit 1"));
        return info == null ? null : info.getId();
    }

    private void fillLeagueNames(List<SeasonInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> leagueMap = leagueInfoMapper.selectList(new LambdaQueryWrapper<LeagueInfo>()
                        .in(LeagueInfo::getId, list.stream().map(SeasonInfo::getLeagueId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(LeagueInfo::getId, LeagueInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setLeagueName(leagueMap.get(item.getLeagueId())));
    }
}
