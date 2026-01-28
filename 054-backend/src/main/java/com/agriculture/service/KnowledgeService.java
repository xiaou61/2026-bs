package com.agriculture.service;

import com.agriculture.entity.Knowledge;
import com.agriculture.mapper.KnowledgeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeService extends ServiceImpl<KnowledgeMapper, Knowledge> {

    public Page<Knowledge> getPage(Integer pageNum, Integer pageSize, String title, String category) {
        Page<Knowledge> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like(Knowledge::getTitle, title);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Knowledge::getCategory, category);
        }
        wrapper.orderByDesc(Knowledge::getCreateTime);
        return this.page(page, wrapper);
    }

    public void incrementView(Long id) {
        this.lambdaUpdate().eq(Knowledge::getId, id).setSql("view_count = view_count + 1").update();
    }

    public void like(Long id) {
        this.lambdaUpdate().eq(Knowledge::getId, id).setSql("like_count = like_count + 1").update();
    }

    public void collect(Long id) {
        this.lambdaUpdate().eq(Knowledge::getId, id).setSql("collect_count = collect_count + 1").update();
    }
}
