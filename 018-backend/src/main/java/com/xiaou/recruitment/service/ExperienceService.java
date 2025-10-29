package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Experience;
import com.xiaou.recruitment.mapper.ExperienceMapper;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService extends ServiceImpl<ExperienceMapper, Experience> {

    public boolean publishExperience(Experience experience) {
        experience.setLikes(0);
        experience.setViews(0);
        return save(experience);
    }

    public Experience getExperienceById(Long id) {
        Experience experience = getById(id);
        if (experience != null) {
            experience.setViews(experience.getViews() + 1);
            updateById(experience);
        }
        return experience;
    }

    public IPage<Experience> getExperienceList(Integer page, Integer size, String type, String keyword) {
        Page<Experience> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Experience> wrapper = new LambdaQueryWrapper<>();
        if (type != null && !type.isEmpty()) {
            wrapper.eq(Experience::getType, type);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Experience::getTitle, keyword)
                    .or().like(Experience::getContent, keyword)
                    .or().like(Experience::getCompanyName, keyword));
        }
        wrapper.orderByDesc(Experience::getCreatedAt);
        return page(pageParam, wrapper);
    }

    public boolean likeExperience(Long id) {
        Experience experience = getById(id);
        if (experience != null) {
            experience.setLikes(experience.getLikes() + 1);
            return updateById(experience);
        }
        return false;
    }
}
