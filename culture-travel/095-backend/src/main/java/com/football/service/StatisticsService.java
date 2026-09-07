package com.football.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.football.entity.MatchSchedule;
import com.football.entity.PlayerInfo;
import com.football.entity.SeasonInfo;
import com.football.entity.StandingRecord;
import com.football.entity.TeamInfo;
import com.football.mapper.ClubInfoMapper;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.PlayerInfoMapper;
import com.football.mapper.SeasonInfoMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private ClubInfoMapper clubInfoMapper;

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Resource
    private UserService userService;

    @Resource
    private StandingService standingService;

    public Map<String, Object> dashboard() {
        List<MatchSchedule> finishedMatches = matchScheduleMapper.selectList(new LambdaQueryWrapper<MatchSchedule>()
                .eq(MatchSchedule::getStatus, "FINISHED"));
        int totalGoals = finishedMatches.stream()
                .map(item -> (item.getHomeScore() == null ? 0 : item.getHomeScore()) + (item.getAwayScore() == null ? 0 : item.getAwayScore()))
                .reduce(0, Integer::sum);
        Map<String, Object> map = new HashMap<>();
        map.put("totalMatches", matchScheduleMapper.selectCount(new LambdaQueryWrapper<>()));
        map.put("finishedMatches", (long) finishedMatches.size());
        map.put("totalGoals", totalGoals);
        map.put("totalTeams", teamInfoMapper.selectCount(new LambdaQueryWrapper<>()));
        map.put("totalClubs", clubInfoMapper.selectCount(new LambdaQueryWrapper<>()));
        map.put("totalFans", userService.countFans());
        map.put("activeSeasons", seasonInfoMapper.selectCount(new LambdaQueryWrapper<SeasonInfo>().eq(SeasonInfo::getStatus, 1)));
        return map;
    }

    public List<Map<String, Object>> matchTrend(Integer days) {
        int finalDays = days == null || days <= 0 ? 7 : days;
        List<MatchSchedule> matches = matchScheduleMapper.selectList(new LambdaQueryWrapper<>());
        Map<LocalDate, List<MatchSchedule>> dateMap = matches.stream()
                .filter(item -> item.getKickOffTime() != null)
                .collect(Collectors.groupingBy(item -> item.getKickOffTime().toLocalDate()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = finalDays - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<MatchSchedule> dayList = dateMap.getOrDefault(date, new ArrayList<>());
            long finished = dayList.stream().filter(item -> "FINISHED".equalsIgnoreCase(item.getStatus())).count();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("date", date.toString());
            item.put("matchCount", dayList.size());
            item.put("finishedCount", finished);
            result.add(item);
        }
        return result;
    }

    public List<StandingRecord> standingRank(Long seasonId) {
        List<StandingRecord> list = standingService.listBySeason(seasonId);
        return list.size() > 8 ? list.subList(0, 8) : list;
    }

    public List<Map<String, Object>> goalRank() {
        List<PlayerInfo> players = playerInfoMapper.selectList(new LambdaQueryWrapper<PlayerInfo>()
                .eq(PlayerInfo::getStatus, 1)
                .orderByDesc(PlayerInfo::getGoalCount)
                .orderByDesc(PlayerInfo::getAssistCount)
                .last("limit 10"));
        Map<Long, String> teamMap = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                        .in(TeamInfo::getId, players.stream().map(PlayerInfo::getTeamId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(TeamInfo::getId, TeamInfo::getTeamName, (a, b) -> a, HashMap::new));
        return players.stream().map(item -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("playerName", item.getName());
            map.put("teamName", teamMap.get(item.getTeamId()));
            map.put("goals", item.getGoalCount() == null ? 0 : item.getGoalCount());
            map.put("assists", item.getAssistCount() == null ? 0 : item.getAssistCount());
            return map;
        }).collect(Collectors.toList());
    }
}
