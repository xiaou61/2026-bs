package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.ClubInfo;
import com.football.entity.SeasonInfo;
import com.football.entity.TeamInfo;
import com.football.entity.VenueInfo;
import com.football.mapper.ClubInfoMapper;
import com.football.mapper.CoachInfoMapper;
import com.football.mapper.FanFollowMapper;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.PlayerInfoMapper;
import com.football.mapper.SeasonInfoMapper;
import com.football.mapper.StandingRecordMapper;
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
public class TeamService {

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    @Resource
    private ClubInfoMapper clubInfoMapper;

    @Resource
    private VenueInfoMapper venueInfoMapper;

    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Resource
    private CoachInfoMapper coachInfoMapper;

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    @Resource
    private StandingRecordMapper standingRecordMapper;

    @Resource
    private FanFollowMapper fanFollowMapper;

    public PageResult<TeamInfo> page(Integer pageNum, Integer pageSize, Long seasonId, Long clubId, String teamName, Integer status) {
        Page<TeamInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TeamInfo> wrapper = new LambdaQueryWrapper<TeamInfo>()
                .eq(seasonId != null, TeamInfo::getSeasonId, seasonId)
                .eq(clubId != null, TeamInfo::getClubId, clubId)
                .like(StringUtils.hasText(teamName), TeamInfo::getTeamName, teamName == null ? null : teamName.trim())
                .eq(status != null, TeamInfo::getStatus, status)
                .orderByDesc(TeamInfo::getId);
        Page<TeamInfo> resultPage = teamInfoMapper.selectPage(page, wrapper);
        fillDetails(resultPage.getRecords());
        PageResult<TeamInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<TeamInfo> listAll(Long seasonId) {
        List<TeamInfo> list = teamInfoMapper.selectList(new LambdaQueryWrapper<TeamInfo>()
                .eq(seasonId != null, TeamInfo::getSeasonId, seasonId)
                .eq(TeamInfo::getStatus, 1)
                .orderByDesc(TeamInfo::getId));
        fillDetails(list);
        return list;
    }

    public TeamInfo requireById(Long id) {
        TeamInfo info = teamInfoMapper.selectById(id);
        if (info == null) {
            throw new BusinessException("球队不存在");
        }
        return info;
    }

    public void save(TeamInfo info) {
        if (info == null || info.getSeasonId() == null || info.getClubId() == null || info.getVenueId() == null || !StringUtils.hasText(info.getTeamName())) {
            throw new BusinessException("球队信息不完整");
        }
        if (seasonInfoMapper.selectById(info.getSeasonId()) == null) {
            throw new BusinessException("赛季不存在");
        }
        if (clubInfoMapper.selectById(info.getClubId()) == null) {
            throw new BusinessException("俱乐部不存在");
        }
        if (venueInfoMapper.selectById(info.getVenueId()) == null) {
            throw new BusinessException("球场不存在");
        }
        TeamInfo exists = teamInfoMapper.selectOne(new LambdaQueryWrapper<TeamInfo>()
                .eq(TeamInfo::getSeasonId, info.getSeasonId())
                .eq(TeamInfo::getTeamName, info.getTeamName().trim())
                .last("limit 1"));
        if (exists != null && (info.getId() == null || !exists.getId().equals(info.getId()))) {
            throw new BusinessException("同一赛季下球队名称已存在");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getTeamNo())) {
                info.setTeamNo("T" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            teamInfoMapper.insert(info);
            return;
        }
        TeamInfo db = requireById(info.getId());
        db.setSeasonId(info.getSeasonId());
        db.setClubId(info.getClubId());
        db.setVenueId(info.getVenueId());
        db.setTeamName(info.getTeamName().trim());
        db.setHomeColor(StringUtils.hasText(info.getHomeColor()) ? info.getHomeColor().trim() : null);
        db.setAwayColor(StringUtils.hasText(info.getAwayColor()) ? info.getAwayColor().trim() : null);
        db.setGoalTarget(StringUtils.hasText(info.getGoalTarget()) ? info.getGoalTarget().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        teamInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (playerInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.PlayerInfo>().eq(com.football.entity.PlayerInfo::getTeamId, id)) > 0) {
            throw new BusinessException("该球队下存在球员，无法删除");
        }
        if (coachInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.CoachInfo>().eq(com.football.entity.CoachInfo::getTeamId, id)) > 0) {
            throw new BusinessException("该球队下存在教练，无法删除");
        }
        if (matchScheduleMapper.selectCount(new LambdaQueryWrapper<com.football.entity.MatchSchedule>()
                .and(wrapper -> wrapper.eq(com.football.entity.MatchSchedule::getHomeTeamId, id).or().eq(com.football.entity.MatchSchedule::getAwayTeamId, id))) > 0) {
            throw new BusinessException("该球队存在比赛记录，无法删除");
        }
        if (standingRecordMapper.selectCount(new LambdaQueryWrapper<com.football.entity.StandingRecord>().eq(com.football.entity.StandingRecord::getTeamId, id)) > 0) {
            throw new BusinessException("该球队存在积分榜记录，无法删除");
        }
        if (fanFollowMapper.selectCount(new LambdaQueryWrapper<com.football.entity.FanFollow>().eq(com.football.entity.FanFollow::getTeamId, id)) > 0) {
            throw new BusinessException("该球队存在关注记录，无法删除");
        }
        teamInfoMapper.deleteById(id);
    }

    public Long countAll() {
        return teamInfoMapper.selectCount(new LambdaQueryWrapper<>());
    }

    private void fillDetails(List<TeamInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> seasonMap = seasonInfoMapper.selectList(new LambdaQueryWrapper<SeasonInfo>()
                        .in(SeasonInfo::getId, list.stream().map(TeamInfo::getSeasonId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SeasonInfo::getId, SeasonInfo::getSeasonName, (a, b) -> a, HashMap::new));
        Map<Long, String> clubMap = clubInfoMapper.selectList(new LambdaQueryWrapper<ClubInfo>()
                        .in(ClubInfo::getId, list.stream().map(TeamInfo::getClubId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(ClubInfo::getId, ClubInfo::getClubName, (a, b) -> a, HashMap::new));
        Map<Long, String> venueMap = venueInfoMapper.selectList(new LambdaQueryWrapper<VenueInfo>()
                        .in(VenueInfo::getId, list.stream().map(TeamInfo::getVenueId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VenueInfo::getId, VenueInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setSeasonName(seasonMap.get(item.getSeasonId()));
            item.setClubName(clubMap.get(item.getClubId()));
            item.setVenueName(venueMap.get(item.getVenueId()));
        });
    }
}
