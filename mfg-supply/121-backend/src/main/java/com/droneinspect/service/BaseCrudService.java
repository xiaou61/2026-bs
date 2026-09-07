package com.droneinspect.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public abstract class BaseCrudService<T> {
    protected abstract BaseMapper<T> mapper();
    protected abstract String[] keywordColumns();

    public IPage<T> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(item -> {
                String[] columns = keywordColumns();
                for (int i = 0; i < columns.length; i++) {
                    if (i == 0) item.like(columns[i], keyword);
                    else item.or().like(columns[i], keyword);
                }
            });
        }
        if (StringUtils.hasText(status)) wrapper.eq("status", status);
        wrapper.orderByDesc("id");
        return mapper().selectPage(new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize), wrapper);
    }

    public void save(T entity) {
        if (id(entity) == null) mapper().insert(entity);
        else mapper().updateById(entity);
    }

    public void delete(Long id) {
        mapper().deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper().update(null, new UpdateWrapper<T>().eq("id", id).set("status", status));
    }

    private Object id(T entity) {
        try {
            Method method = entity.getClass().getMethod("getId");
            return method.invoke(entity);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
