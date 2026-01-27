package com.online.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.Course;
import com.online.entity.Favorite;
import com.online.mapper.CourseMapper;
import com.online.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService extends ServiceImpl<FavoriteMapper, Favorite> {
    @Autowired
    private CourseMapper courseMapper;

    public void addFavorite(Long userId, Long courseId) {
        Favorite exist = this.getOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getCourseId, courseId));
        if (exist != null) {
            throw new RuntimeException("已收藏");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setCourseId(courseId);
        this.save(favorite);
    }

    public void removeFavorite(Long userId, Long courseId) {
        this.remove(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getCourseId, courseId));
    }

    public Page<Favorite> getList(Long userId, Integer pageNum, Integer pageSize) {
        Page<Favorite> page = new Page<>(pageNum, pageSize);
        Page<Favorite> result = this.page(page, new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .orderByDesc(Favorite::getCreateTime));
        result.getRecords().forEach(fav -> {
            Course course = courseMapper.selectById(fav.getCourseId());
            fav.setCourse(course);
        });
        return result;
    }

    public boolean checkFavorite(Long userId, Long courseId) {
        return this.count(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getCourseId, courseId)) > 0;
    }
}
