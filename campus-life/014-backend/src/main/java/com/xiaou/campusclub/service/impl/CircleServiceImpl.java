package com.xiaou.campusclub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusclub.dto.CircleRequest;
import com.xiaou.campusclub.entity.Circle;
import com.xiaou.campusclub.entity.CircleMember;
import com.xiaou.campusclub.entity.UserInterest;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.CircleMapper;
import com.xiaou.campusclub.mapper.CircleMemberMapper;
import com.xiaou.campusclub.mapper.UserInterestMapper;
import com.xiaou.campusclub.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CircleServiceImpl implements CircleService {
    
    @Autowired
    private CircleMapper circleMapper;
    
    @Autowired
    private CircleMemberMapper circleMemberMapper;
    
    @Autowired
    private UserInterestMapper userInterestMapper;
    
    @Override
    public IPage<Circle> getCircleList(Integer page, Integer size, String category) {
        Page<Circle> circlePage = new Page<>(page, size);
        LambdaQueryWrapper<Circle> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Circle::getCategory, category);
        }
        
        wrapper.orderByDesc(Circle::getMemberCount);
        
        return circleMapper.selectPage(circlePage, wrapper);
    }
    
    @Override
    public Circle getCircleDetail(Long circleId) {
        Circle circle = circleMapper.selectById(circleId);
        if (circle == null) {
            throw new BusinessException("圈子不存在");
        }
        return circle;
    }
    
    @Override
    @Transactional
    public Long createCircle(Long userId, CircleRequest request) {
        Circle circle = new Circle();
        circle.setName(request.getName());
        circle.setCategory(request.getCategory());
        circle.setCover(request.getCover());
        circle.setDescription(request.getDescription());
        circle.setCreatorId(userId);
        circle.setMemberCount(1);
        circle.setTopicCount(0);
        circle.setCreateTime(LocalDateTime.now());
        
        circleMapper.insert(circle);
        
        CircleMember member = new CircleMember();
        member.setCircleId(circle.getId());
        member.setUserId(userId);
        member.setJoinTime(LocalDateTime.now());
        circleMemberMapper.insert(member);
        
        return circle.getId();
    }
    
    @Override
    @Transactional
    public void joinCircle(Long circleId, Long userId) {
        Circle circle = circleMapper.selectById(circleId);
        if (circle == null) {
            throw new BusinessException("圈子不存在");
        }
        
        LambdaQueryWrapper<CircleMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircleMember::getCircleId, circleId)
               .eq(CircleMember::getUserId, userId);
        
        if (circleMemberMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已加入该圈子");
        }
        
        CircleMember member = new CircleMember();
        member.setCircleId(circleId);
        member.setUserId(userId);
        member.setJoinTime(LocalDateTime.now());
        circleMemberMapper.insert(member);
        
        circle.setMemberCount(circle.getMemberCount() + 1);
        circleMapper.updateById(circle);
    }
    
    @Override
    @Transactional
    public void leaveCircle(Long circleId, Long userId) {
        LambdaQueryWrapper<CircleMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircleMember::getCircleId, circleId)
               .eq(CircleMember::getUserId, userId);
        circleMemberMapper.delete(wrapper);
        
        Circle circle = circleMapper.selectById(circleId);
        if (circle != null && circle.getMemberCount() > 0) {
            circle.setMemberCount(circle.getMemberCount() - 1);
            circleMapper.updateById(circle);
        }
    }
    
    @Override
    public List<Circle> getMyCircles(Long userId) {
        LambdaQueryWrapper<CircleMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircleMember::getUserId, userId);
        List<CircleMember> members = circleMemberMapper.selectList(wrapper);
        
        return members.stream()
                .map(member -> circleMapper.selectById(member.getCircleId()))
                .filter(circle -> circle != null)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Circle> getRecommendCircles(Long userId) {
        LambdaQueryWrapper<Circle> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Circle::getMemberCount)
               .last("limit 10");
        return circleMapper.selectList(wrapper);
    }
}

