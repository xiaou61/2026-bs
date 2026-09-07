package com.xiaou.campusclub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.dto.CircleRequest;
import com.xiaou.campusclub.entity.Circle;

import java.util.List;

public interface CircleService {
    IPage<Circle> getCircleList(Integer page, Integer size, String category);
    Circle getCircleDetail(Long circleId);
    Long createCircle(Long userId, CircleRequest request);
    void joinCircle(Long circleId, Long userId);
    void leaveCircle(Long circleId, Long userId);
    List<Circle> getMyCircles(Long userId);
    List<Circle> getRecommendCircles(Long userId);
}

