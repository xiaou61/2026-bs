package com.xiaou.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Category;
import com.xiaou.entity.Competition;
import com.xiaou.entity.Work;
import com.xiaou.mapper.CategoryMapper;
import com.xiaou.mapper.CompetitionMapper;
import com.xiaou.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private WorkMapper workMapper;

    public Page<Competition> getList(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status) {
        Page<Competition> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Competition> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Competition::getTitle, keyword);
        }
        if (categoryId != null) {
            wrapper.eq(Competition::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper.eq(Competition::getStatus, status);
        }
        wrapper.orderByDesc(Competition::getCreateTime);
        Page<Competition> result = competitionMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillCompetitionInfo);
        return result;
    }

    public List<Competition> getPublicList(Long categoryId) {
        LambdaQueryWrapper<Competition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Competition::getStatus, 1);
        if (categoryId != null) {
            wrapper.eq(Competition::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(Competition::getCreateTime);
        List<Competition> list = competitionMapper.selectList(wrapper);
        list.forEach(this::fillCompetitionInfo);
        return list;
    }

    public Competition getById(Long id) {
        Competition competition = competitionMapper.selectById(id);
        if (competition != null) {
            fillCompetitionInfo(competition);
        }
        return competition;
    }

    public void save(Competition competition) {
        if (competition.getId() == null) {
            competitionMapper.insert(competition);
        } else {
            competitionMapper.updateById(competition);
        }
    }

    public void delete(Long id) {
        competitionMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        Competition competition = new Competition();
        competition.setId(id);
        competition.setStatus(status);
        competitionMapper.updateById(competition);
    }

    public void publishResult(Long id) {
        Competition competition = new Competition();
        competition.setId(id);
        competition.setPublishResult(1);
        competitionMapper.updateById(competition);
    }

    private void fillCompetitionInfo(Competition competition) {
        if (competition.getCategoryId() != null) {
            Category category = categoryMapper.selectById(competition.getCategoryId());
            if (category != null) {
                competition.setCategoryName(category.getName());
            }
        }
        Long workCount = workMapper.selectCount(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competition.getId()));
        competition.setWorkCount(workCount.intValue());
    }
}
