package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.VenueInfo;
import com.football.mapper.MatchScheduleMapper;
import com.football.mapper.TeamInfoMapper;
import com.football.mapper.VenueInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VenueService {

    @Resource
    private VenueInfoMapper venueInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    @Resource
    private MatchScheduleMapper matchScheduleMapper;

    public PageResult<VenueInfo> page(Integer pageNum, Integer pageSize, String name, String city, Integer status) {
        Page<VenueInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<VenueInfo> wrapper = new LambdaQueryWrapper<VenueInfo>()
                .like(StringUtils.hasText(name), VenueInfo::getName, name == null ? null : name.trim())
                .like(StringUtils.hasText(city), VenueInfo::getCity, city == null ? null : city.trim())
                .eq(status != null, VenueInfo::getStatus, status)
                .orderByDesc(VenueInfo::getId);
        Page<VenueInfo> resultPage = venueInfoMapper.selectPage(page, wrapper);
        PageResult<VenueInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<VenueInfo> listAll() {
        return venueInfoMapper.selectList(new LambdaQueryWrapper<VenueInfo>()
                .eq(VenueInfo::getStatus, 1)
                .orderByDesc(VenueInfo::getId));
    }

    public VenueInfo requireById(Long id) {
        VenueInfo info = venueInfoMapper.selectById(id);
        if (info == null) {
            throw new BusinessException("球场不存在");
        }
        return info;
    }

    public void save(VenueInfo info) {
        if (info == null || !StringUtils.hasText(info.getName())) {
            throw new BusinessException("球场名称不能为空");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getVenueNo())) {
                info.setVenueNo("V" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            venueInfoMapper.insert(info);
            return;
        }
        VenueInfo db = requireById(info.getId());
        db.setName(info.getName().trim());
        db.setCity(StringUtils.hasText(info.getCity()) ? info.getCity().trim() : null);
        db.setAddress(StringUtils.hasText(info.getAddress()) ? info.getAddress().trim() : null);
        db.setCapacity(info.getCapacity());
        db.setTurfType(StringUtils.hasText(info.getTurfType()) ? info.getTurfType().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        venueInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (teamInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.TeamInfo>().eq(com.football.entity.TeamInfo::getVenueId, id)) > 0) {
            throw new BusinessException("该球场已被球队使用，无法删除");
        }
        if (matchScheduleMapper.selectCount(new LambdaQueryWrapper<com.football.entity.MatchSchedule>().eq(com.football.entity.MatchSchedule::getVenueId, id)) > 0) {
            throw new BusinessException("该球场存在比赛安排，无法删除");
        }
        venueInfoMapper.deleteById(id);
    }
}
