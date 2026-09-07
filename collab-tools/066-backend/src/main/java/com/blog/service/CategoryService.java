package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.common.PageResult;
import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private PostMapper postMapper;

    public List<Category> list() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, 1)
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getId));
    }

    public PageResult<Category> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        Page<Category> resultPage = categoryMapper.selectPage(page, new LambdaQueryWrapper<Category>()
                .like(StringUtils.hasText(name), Category::getName, name)
                .eq(status != null, Category::getStatus, status)
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getId));
        PageResult<Category> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(Category category) {
        if (category == null || !StringUtils.hasText(category.getName())) {
            throw new BusinessException("分类名称不能为空");
        }
        category.setName(category.getName().trim());
        category.setSort(category.getSort() == null ? 0 : category.getSort());
        category.setStatus(category.getStatus() == null ? 1 : category.getStatus());
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            Category db = categoryMapper.selectById(category.getId());
            if (db == null) {
                throw new BusinessException("分类不存在");
            }
            categoryMapper.updateById(category);
        }
    }

    public void deleteById(Long id) {
        Long count = postMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getCategoryId, id));
        if (count != null && count > 0) {
            throw new BusinessException("分类下存在文章，不能删除");
        }
        categoryMapper.deleteById(id);
    }

    public Map<Long, String> nameMap(List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        List<Category> list = categoryMapper.selectBatchIds(ids);
        for (Category item : list) {
            map.put(item.getId(), item.getName());
        }
        return map;
    }

    public boolean existsEnabled(Long id) {
        if (id == null) {
            return false;
        }
        return categoryMapper.selectCount(new LambdaQueryWrapper<Category>().eq(Category::getId, id).eq(Category::getStatus, 1)) > 0;
    }
}
