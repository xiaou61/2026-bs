package com.gongkao.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.Subject;
import com.gongkao.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SubjectService {

    private static final String SUBJECT_PUBLIC_CACHE_KEY = "gongkao:subject:public:list";

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Page<Subject> getList(int pageNum, int pageSize, String name, Integer status) {
        Page<Subject> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByAsc("sort").orderByDesc("create_time");
        return subjectMapper.selectPage(page, wrapper);
    }

    public List<Subject> getPublicList() {
        String cacheValue = stringRedisTemplate.opsForValue().get(SUBJECT_PUBLIC_CACHE_KEY);
        if (StringUtils.hasText(cacheValue)) {
            return JSON.parseArray(cacheValue, Subject.class);
        }
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByAsc("sort").orderByDesc("create_time");
        List<Subject> list = subjectMapper.selectList(wrapper);
        stringRedisTemplate.opsForValue().set(SUBJECT_PUBLIC_CACHE_KEY, JSON.toJSONString(list), 30, TimeUnit.MINUTES);
        return list;
    }

    public void add(Subject subject) {
        if (subject.getStatus() == null) {
            subject.setStatus(1);
        }
        if (subject.getSort() == null) {
            subject.setSort(0);
        }
        subjectMapper.insert(subject);
        clearCache();
    }

    public void update(Subject subject) {
        subjectMapper.updateById(subject);
        clearCache();
    }

    public void delete(Long id) {
        subjectMapper.deleteById(id);
        clearCache();
    }

    private void clearCache() {
        stringRedisTemplate.delete(SUBJECT_PUBLIC_CACHE_KEY);
    }
}
