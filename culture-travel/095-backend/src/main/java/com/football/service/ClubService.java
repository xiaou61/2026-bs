package com.football.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.football.common.BusinessException;
import com.football.common.PageResult;
import com.football.entity.ClubInfo;
import com.football.mapper.ClubInfoMapper;
import com.football.mapper.TeamInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClubService {

    @Resource
    private ClubInfoMapper clubInfoMapper;

    @Resource
    private TeamInfoMapper teamInfoMapper;

    public PageResult<ClubInfo> page(Integer pageNum, Integer pageSize, String clubName, String city, Integer status) {
        Page<ClubInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ClubInfo> wrapper = new LambdaQueryWrapper<ClubInfo>()
                .like(StringUtils.hasText(clubName), ClubInfo::getClubName, clubName == null ? null : clubName.trim())
                .like(StringUtils.hasText(city), ClubInfo::getCity, city == null ? null : city.trim())
                .eq(status != null, ClubInfo::getStatus, status)
                .orderByDesc(ClubInfo::getId);
        Page<ClubInfo> resultPage = clubInfoMapper.selectPage(page, wrapper);
        PageResult<ClubInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<ClubInfo> listAll() {
        return clubInfoMapper.selectList(new LambdaQueryWrapper<ClubInfo>()
                .eq(ClubInfo::getStatus, 1)
                .orderByDesc(ClubInfo::getId));
    }

    public ClubInfo requireById(Long id) {
        ClubInfo info = clubInfoMapper.selectById(id);
        if (info == null) {
            throw new BusinessException("俱乐部不存在");
        }
        return info;
    }

    public void save(ClubInfo info) {
        if (info == null || !StringUtils.hasText(info.getClubName())) {
            throw new BusinessException("俱乐部名称不能为空");
        }
        if (info.getId() == null) {
            if (!StringUtils.hasText(info.getClubNo())) {
                info.setClubNo("C" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
            }
            info.setStatus(info.getStatus() == null ? 1 : info.getStatus());
            clubInfoMapper.insert(info);
            return;
        }
        ClubInfo db = requireById(info.getId());
        db.setClubName(info.getClubName().trim());
        db.setShortName(StringUtils.hasText(info.getShortName()) ? info.getShortName().trim() : null);
        db.setCity(StringUtils.hasText(info.getCity()) ? info.getCity().trim() : null);
        db.setFoundedYear(info.getFoundedYear());
        db.setChairman(StringUtils.hasText(info.getChairman()) ? info.getChairman().trim() : null);
        db.setContactPhone(StringUtils.hasText(info.getContactPhone()) ? info.getContactPhone().trim() : null);
        db.setDescription(StringUtils.hasText(info.getDescription()) ? info.getDescription().trim() : null);
        if (info.getStatus() != null) {
            db.setStatus(info.getStatus());
        }
        clubInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (teamInfoMapper.selectCount(new LambdaQueryWrapper<com.football.entity.TeamInfo>().eq(com.football.entity.TeamInfo::getClubId, id)) > 0) {
            throw new BusinessException("该俱乐部下存在球队，无法删除");
        }
        clubInfoMapper.deleteById(id);
    }

    public Long countAll() {
        return clubInfoMapper.selectCount(new LambdaQueryWrapper<>());
    }
}
