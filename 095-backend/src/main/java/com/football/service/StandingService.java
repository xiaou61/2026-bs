package com.football.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.PageResult;
import com.football.entity.MatchSchedule;
import com.football.entity.SeasonInfo;
import com.football.entity.StandingRecord;
import com.football.entity.TeamInfo;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.SeasonInfoMapper;
import com.football.mapper.StandingRecordMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StandingService {

    @Resource
    private StandingRecordMapper standingRecordMapper;

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    public PageResult<StandingRecord> page(Integer pageNum, Integer pageSize, Long seasonId, String teamName) {
        Long finalSeasonId = seasonId == null ? getDefaultSeasonId() : seasonId;
        Page<StandingRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<StandingRecord> wrapper = new LambdaQueryWrapper<StandingRecord>()
                .eq(finalSeasonId != null, StandingRecord::getSeasonId, finalSeasonId)
                .orderByAsc(StandingRecord::getRanking);
        if (StringUtils.hasText(teamName)) {
            List<Long> teamIds = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>().like(TeamInfo::getTeamName, teamName.trim()))
                    .stream()
                    .map(TeamInfo::getId)
                    .collect(Collectors.toList());
            if (teamIds.isEmpty()) {
                PageResult<StandingRecord> empty = new PageResult<>();
                empty.setTotal(0L);
                empty.setRecords(new ArrayList<>());
                return empty;
            }
            wrapper.in(StandingRecord::getTeamId, teamIds);
        }
        Page<StandingRecord> resultPage = standingRecordMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<StandingRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<StandingRecord> listBySeason(Long seasonId) {
        Long finalSeasonId = seasonId == null ? getDefaultSeasonId() : seasonId;
        List<StandingRecord> list = standingRecordMapper.selectList(new LambdaQueryWrapper<StandingRecord>()
                .eq(finalSeasonId != null, StandingRecord::getSeasonId, finalSeasonId)
                .orderByAsc(StandingRecord::getRanking));
        fillDetails(list);
        return list;
    }

    public void rebuildSeasonStandings(Long seasonId) {
        if (seasonId == null) {
            return;
        }
        List<TeamInfo> teams = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                .eq(TeamInfo::getSeasonId, seasonId)
                .eq(TeamInfo::getStatus, 1));
        standingRecordMapper.delete(new LambdaQueryWrapper<StandingRecord>().eq(StandingRecord::getSeasonId, seasonId));
        if (teams.isEmpty()) {
            return;
        }
        Map<Long, StandingRecord> recordMap = new HashMap<>();
        for (TeamInfo team : teams) {
            StandingRecord record = new StandingRecord();
            record.setSeasonId(seasonId);
            record.setTeamId(team.getId());
            record.setPlayedCount(0);
            record.setWinCount(0);
            record.setDrawCount(0);
            record.setLossCount(0);
            record.setGoalFor(0);
            record.setGoalAgainst(0);
            record.setGoalDiff(0);
            record.setPoints(0);
            recordMap.put(team.getId(), record);
        }
        List<MatchSchedule> matches = matchScheduleMapper.selectList(new LambdaQueryWrapper<MatchSchedule>()
                .eq(MatchSchedule::getSeasonId, seasonId)
                .eq(MatchSchedule::getStatus, "FINISHED"));
        for (MatchSchedule match : matches) {
            StandingRecord home = recordMap.get(match.getHomeTeamId());
            StandingRecord away = recordMap.get(match.getAwayTeamId());
            if (home == null || away == null) {
                continue;
            }
            int homeScore = match.getHomeScore() == null ? 0 : match.getHomeScore();
            int awayScore = match.getAwayScore() == null ? 0 : match.getAwayScore();
            home.setPlayedCount(home.getPlayedCount() + 1);
            away.setPlayedCount(away.getPlayedCount() + 1);
            home.setGoalFor(home.getGoalFor() + homeScore);
            home.setGoalAgainst(home.getGoalAgainst() + awayScore);
            away.setGoalFor(away.getGoalFor() + awayScore);
            away.setGoalAgainst(away.getGoalAgainst() + homeScore);
            if (homeScore > awayScore) {
                home.setWinCount(home.getWinCount() + 1);
                away.setLossCount(away.getLossCount() + 1);
                home.setPoints(home.getPoints() + 3);
            } else if (homeScore < awayScore) {
                away.setWinCount(away.getWinCount() + 1);
                home.setLossCount(home.getLossCount() + 1);
                away.setPoints(away.getPoints() + 3);
            } else {
                home.setDrawCount(home.getDrawCount() + 1);
                away.setDrawCount(away.getDrawCount() + 1);
                home.setPoints(home.getPoints() + 1);
                away.setPoints(away.getPoints() + 1);
            }
        }
        List<StandingRecord> records = new ArrayList<>(recordMap.values());
        for (StandingRecord item : records) {
            item.setGoalDiff(item.getGoalFor() - item.getGoalAgainst());
        }
        records.sort(Comparator
                .comparing(StandingRecord::getPoints, Comparator.reverseOrder())
                .thenComparing(StandingRecord::getGoalDiff, Comparator.reverseOrder())
                .thenComparing(StandingRecord::getGoalFor, Comparator.reverseOrder())
                .thenComparing(StandingRecord::getTeamId));
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setRanking(i + 1);
            standingRecordMapper.insert(records.get(i));
        }
    }

    public Long getDefaultSeasonId() {
        SeasonInfo season = seasonInfoMapper.selectOne(new LambdaQueryWrapper<SeasonInfo>()
                .eq(SeasonInfo::getStatus, 1)
                .orderByDesc(SeasonInfo::getId)
                .last("limit 1"));
        return season == null ? null : season.getId();
    }

    private void fillDetails(List<StandingRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> seasonMap = seasonInfoMapper.selectList(new LambdaQueryWrapper<SeasonInfo>()
                        .in(SeasonInfo::getId, list.stream().map(StandingRecord::getSeasonId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SeasonInfo::getId, SeasonInfo::getSeasonName, (a, b) -> a, HashMap::new));
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                        .in(TeamInfo::getId, list.stream().map(StandingRecord::getTeamId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setSeasonName(seasonMap.get(item.getSeasonId()));
            item.setTeamName(teamMap.get(item.getTeamId()));
        });
    }
}
