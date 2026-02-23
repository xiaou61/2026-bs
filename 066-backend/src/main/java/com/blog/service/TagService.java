package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.common.PageResult;
import com.blog.entity.PostTag;
import com.blog.entity.Tag;
import com.blog.mapper.PostTagMapper;
import com.blog.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private PostTagMapper postTagMapper;

    public List<Tag> list() {
        return tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .eq(Tag::getStatus, 1)
                .orderByDesc(Tag::getId));
    }

    public PageResult<Tag> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<Tag> page = new Page<>(pageNum, pageSize);
        Page<Tag> resultPage = tagMapper.selectPage(page, new LambdaQueryWrapper<Tag>()
                .like(StringUtils.hasText(name), Tag::getName, name)
                .eq(status != null, Tag::getStatus, status)
                .orderByDesc(Tag::getId));
        PageResult<Tag> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(Tag tag) {
        if (tag == null || !StringUtils.hasText(tag.getName())) {
            throw new BusinessException("标签名称不能为空");
        }
        tag.setName(tag.getName().trim());
        tag.setStatus(tag.getStatus() == null ? 1 : tag.getStatus());
        if (tag.getId() == null) {
            tagMapper.insert(tag);
        } else {
            Tag db = tagMapper.selectById(tag.getId());
            if (db == null) {
                throw new BusinessException("标签不存在");
            }
            tagMapper.updateById(tag);
        }
    }

    public void deleteById(Long id) {
        Long count = postTagMapper.selectCount(new LambdaQueryWrapper<PostTag>().eq(PostTag::getTagId, id));
        if (count != null && count > 0) {
            throw new BusinessException("标签已被文章使用，不能删除");
        }
        tagMapper.deleteById(id);
    }

    public Map<Long, String> nameMap(List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        List<Tag> list = tagMapper.selectBatchIds(ids);
        for (Tag tag : list) {
            map.put(tag.getId(), tag.getName());
        }
        return map;
    }

    public boolean existsEnabled(Long id) {
        if (id == null) {
            return false;
        }
        return tagMapper.selectCount(new LambdaQueryWrapper<Tag>().eq(Tag::getId, id).eq(Tag::getStatus, 1)) > 0;
    }
}
