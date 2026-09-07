package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.LeagueInfo;
import com.football.mapper.LeagueInfoMapper;
import com.football.mapper.SeasonInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LeagueService {

    @Resource
    private LeagueInfoMapper leagueInfoMapper;

    @Resource
    private SeasonInfoMapper seasonInfoMapper;

    public PageResult<LeagueInfo> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<LeagueInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LeagueInfo> wrapper = new LambdaQueryWrapper<LeagueInfo>()
                .like(StringUtils.hasText(name), LeagueInfo::getName, name == null ? null : name.trim())
                .eq(status != null, LeagueInfo::getStatus, status)
                .orderByDesc(LeagueInfo::getId);
        Page<LeagueInfo> resultPage = leagueInfoMapper.selectPage(page, wrapper);
        PageResult<LeagueInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<LeagueInfo> listAll() {
        return leagueInfoMapper.selectList(new LambdaQueryWrapper<LeagueInfo>()
                .eq(LeagueInfo::getStatus, 1)
                .orderByDesc(LeagueInfo::getId));
    }

    public LeagueInfo requireById(Long id) {
        LeagueInfo info = leagueInfoMapper.selectById(id);
        if (info == null) {
            throw new BusinessException("联赛不存在");
        }
        return info;
    }

    public void save(LeagueInfo info) {
        if (info == null || !StringUtils.hasText(info.getName())) {
            throw new BusinessException("联赛名称不能为空");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getLeagueNo())) {
                info.setLeagueNo("L" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            leagueInfoMapper.insert(info);
            return;
        }
        LeagueInfo db = requireById(info.getId());
        db.setName(info.getName().trim());
        db.setRegion(StringUtils.hasText(info.getRegion()) ? info.getRegion().trim() : null);
        db.setOrganizer(StringUtils.hasText(info.getOrganizer()) ? info.getOrganizer().trim() : null);
        db.setLevelType(StringUtils.hasText(info.getLevelType()) ? info.getLevelType().trim() : null);
        db.setStartDate(info.getStartDate());
        db.setEndDate(info.getEndDate());
        db.setRemark(StringUtils.hasText(info.getRemark()) ? info.getRemark().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        leagueInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (seasonInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.SeasonInfo>().eq(com.football.entity.SeasonInfo::getLeagueId, id)) > 0) {
            throw new BusinessException("该联赛下存在赛季，无法删除");
        }
        leagueInfoMapper.deleteById(id);
    }

    public Long countAll() {
        return leagueInfoMapper.selectCount(new LambdaQueryWrapper<>());
    }
}
