package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.dto.MatchResultDTO;
import com.football.entity.MatchSchedule;
import com.football.entity.SeasonInfo;
import com.football.entity.TeamInfo;
import com.football.entity.VenueInfo;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.SeasonInfoMapper;
import com.football.mapper.TeamInfoMapper;
import com.football.mapper.VenueInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private VenueInfoMapper venueInfoMapper;

    @Resource
    private StandingService standingService;

    public PageResult<MatchSchedule> page(Integer pageNum, Integer pageSize, Long seasonId, String status, Long teamId) {
        Page<MatchSchedule> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MatchSchedule> wrapper = new LambdaQueryWrapper<MatchSchedule>()
                .eq(seasonId != null, MatchSchedule::getSeasonId, seasonId)
                .eq(StringUtils.hasText(status), MatchSchedule::getStatus, status == null ? null : status.trim().toUpperCase())
                .orderByDesc(MatchSchedule::getKickOffTime);
        if (teamId != null) {
            wrapper.and(item -> item.eq(MatchSchedule::getHomeTeamId, teamId).or().eq(MatchSchedule::getAwayTeamId, teamId));
        }
        Page<MatchSchedule> resultPage = matchScheduleMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<MatchSchedule> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<MatchSchedule> publicList(Long seasonId) {
        List<MatchSchedule> list = matchScheduleMapper.selectList(new LambdaQueryWrapper<MatchSchedule>()
                .eq(seasonId != null, MatchSchedule::getSeasonId, seasonId)
                .orderByAsc(MatchSchedule::getKickOffTime)
                .last("limit 12"));
        fillDetails(list);
        return list;
    }

    public MatchSchedule requireById(Long id) {
        MatchSchedule match = matchScheduleMapper.selectById(id);
        if (match == null) {
            throw new BusinessException("比赛不存在");
        }
        return match;
    }

    public void save(MatchSchedule match) {
        if (match == null || match.getSeasonId() == null || match.getHomeTeamId() == null || match.getAwayTeamId() == null || match.getVenueId() == null || match.getKickOffTime() == null) {
            throw new BusinessException("比赛信息不完整");
        }
        if (match.getHomeTeamId().equals(match.getAwayTeamId())) {
            throw new BusinessException("主客队不能相同");
        }
        SeasonInfo season = seasonInfoMapper.selectById(match.getSeasonId());
        if (season == null) {
            throw new BusinessException("赛季不存在");
        }
        TeamInfo homeTeam = teamInfoMapper.selectById(match.getHomeTeamId());
        TeamInfo awayTeam = teamInfoMapper.selectById(match.getAwayTeamId());
        if (homeTeam == null || awayTeam == null) {
            throw new BusinessException("球队不存在");
        }
        if (!match.getSeasonId().equals(homeTeam.getSeasonId()) || !match.getSeasonId().equals(awayTeam.getSeasonId())) {
            throw new BusinessException("球队与赛季不匹配");
        }
        if (venueInfoMapper.selectById(match.getVenueId()) == null) {
            throw new BusinessException("球场不存在");
        }
        if (match.getId() == null) {
            if (!StringUtils.hasText(match.getMatchNo())) {
                match.setMatchNo("M" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            match.setStatus(StringUtils.hasText(match.getStatus()) ? match.getStatus().trim().toUpperCase() : "SCHEDULED");
            match.setHomeScore(match.getHomeScore() == null ? 0 : match.getHomeScore());
            match.setAwayScore(match.getAwayScore() == null ? 0 : match.getAwayScore());
            matchScheduleMapper.insert(match);
            return;
        }
        MatchSchedule db = requireById(match.getId());
        db.setSeasonId(match.getSeasonId());
        db.setRoundNo(match.getRoundNo());
        db.setHomeTeamId(match.getHomeTeamId());
        db.setAwayTeamId(match.getAwayTeamId());
        db.setVenueId(match.getVenueId());
        db.setKickOffTime(match.getKickOffTime());
        db.setReferee(StringUtils.hasText(match.getReferee()) ? match.getReferee().trim() : null);
        db.setRemark(StringUtils.hasText(match.getRemark()) ? match.getRemark().trim() : null);
        if (StringUtils.hasText(match.getStatus()) && !"FINISHED".equalsIgnoreCase(match.getStatus())) {
            db.setStatus(match.getStatus().trim().toUpperCase());
        }
        matchScheduleMapper.updateById(db);
    }

    public void updateResult(Long id, MatchResultDTO dto) {
        MatchSchedule match = requireById(id);
        if (dto == null || dto.getHomeScore() == null || dto.getAwayScore() == null || dto.getHomeScore() < 0 || dto.getAwayScore() < 0) {
            throw new BusinessException("比分不合法");
        }
        match.setHomeScore(dto.getHomeScore());
        match.setAwayScore(dto.getAwayScore());
        match.setReferee(StringUtils.hasText(dto.getReferee()) ? dto.getReferee().trim() : match.getReferee());
        match.setRemark(StringUtils.hasText(dto.getRemark()) ? dto.getRemark().trim() : match.getRemark());
        match.setStatus("FINISHED");
        matchScheduleMapper.updateById(match);
        standingService.rebuildSeasonStandings(match.getSeasonId());
    }

    public void deleteById(Long id) {
        MatchSchedule match = requireById(id);
        matchScheduleMapper.deleteById(id);
        if ("FINISHED".equalsIgnoreCase(match.getStatus())) {
            standingService.rebuildSeasonStandings(match.getSeasonId());
        }
    }

    public Long countAll() {
        return matchScheduleMapper.selectCount(new LambdaQueryWrapper<>());
    }

    public Long countFinished() {
        return matchScheduleMapper.selectCount(new LambdaQueryWrapper<MatchSchedule>().eq(MatchSchedule::getStatus, "FINISHED"));
    }

    private void fillDetails(List<MatchSchedule> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> seasonMap = seasonInfoMapper.selectList(new LambdaQueryWrapper<SeasonInfo>()
                        .in(SeasonInfo::getId, list.stream().map(MatchSchedule::getSeasonId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SeasonInfo::getId, SeasonInfo::getSeasonName, (a, b) -> a, HashMap::new));
        List<Long> teamIds = list.stream()
                .flatMap(item -> java.util.stream.Stream.of(item.getHomeTeamId(), item.getAwayTeamId()))
                .distinct()
                .collect(Collectors.toList());
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>().in(TeamInfo::getId, teamIds))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        Map<Long, String> venueMap = venueInfoMapper.selectList(new LambdaQueryWrapper<VenueInfo>()
                        .in(VenueInfo::getId, list.stream().map(MatchSchedule::getVenueId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VenueInfo::getId, VenueInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setSeasonName(seasonMap.get(item.getSeasonId()));
            item.setHomeTeamName(teamMap.get(item.getHomeTeamId()));
            item.setAwayTeamName(teamMap.get(item.getAwayTeamId()));
            item.setVenueName(venueMap.get(item.getVenueId()));
        });
    }
}
