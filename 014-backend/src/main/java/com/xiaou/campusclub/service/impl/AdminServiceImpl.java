package com.xiaou.campusclub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.entity.*;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.*;
import com.xiaou.campusclub.service.AdminService;
import com.xiaou.campusclub.util.JwtUtil;
import com.xiaou.campusclub.util.PasswordUtil;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.StatisticsVO;
import com.xiaou.campusclub.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ClubMapper clubMapper;
    
    @Autowired
    private ActivityMapper activityMapper;
    
    @Autowired
    private TopicMapper topicMapper;
    
    @Autowired
    private CircleMapper circleMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public LoginVO login(LoginRequest request) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, request.getUsername());
        Admin admin = adminMapper.selectOne(wrapper);
        
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        if (!PasswordUtil.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "admin");
        
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserId(admin.getId());
        loginVO.setUsername(admin.getUsername());
        loginVO.setRealName(admin.getRealName());
        
        return loginVO;
    }
    
    @Override
    public IPage<User> getUserList(Integer page, Integer size, String keyword) {
        Page<User> userPage = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getUsername, keyword)
                   .or()
                   .like(User::getRealName, keyword)
                   .or()
                   .like(User::getStudentId, keyword);
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        
        return userMapper.selectPage(userPage, wrapper);
    }
    
    @Override
    @Transactional
    public void updateUserStatus(Long userId, Integer status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    public IPage<Club> getPendingClubs(Integer page, Integer size) {
        Page<Club> clubPage = new Page<>(page, size);
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getStatus, 0)
               .orderByAsc(Club::getCreateTime);
        
        return clubMapper.selectPage(clubPage, wrapper);
    }
    
    @Override
    @Transactional
    public void approveClub(Long clubId, Integer status) {
        Club club = clubMapper.selectById(clubId);
        if (club == null) {
            throw new BusinessException("社团不存在");
        }
        
        club.setStatus(status);
        club.setUpdateTime(LocalDateTime.now());
        clubMapper.updateById(club);
    }
    
    @Override
    public IPage<TopicVO> getTopicList(Integer page, Integer size) {
        Page<Topic> topicPage = new Page<>(page, size);
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getStatus, 0)
               .orderByDesc(Topic::getCreateTime);
        
        IPage<Topic> result = topicMapper.selectPage(topicPage, wrapper);
        
        return result.convert(topic -> {
            TopicVO vo = BeanUtil.copyProperties(topic, TopicVO.class);
            User user = userMapper.selectById(topic.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
            return vo;
        });
    }
    
    @Override
    @Transactional
    public void topTopic(Long topicId, Integer isTop) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        topic.setIsTop(isTop);
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.updateById(topic);
    }
    
    @Override
    @Transactional
    public void deleteTopic(Long topicId) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null) {
            throw new BusinessException("话题不存在");
        }
        
        topic.setStatus(1);
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.updateById(topic);
    }
    
    @Override
    public StatisticsVO getStatistics() {
        StatisticsVO vo = new StatisticsVO();
        vo.setUserCount(userMapper.selectCount(null));
        vo.setClubCount(clubMapper.selectCount(new LambdaQueryWrapper<Club>().eq(Club::getStatus, 1)));
        vo.setActivityCount(activityMapper.selectCount(null));
        vo.setTopicCount(topicMapper.selectCount(new LambdaQueryWrapper<Topic>().eq(Topic::getStatus, 0)));
        vo.setCircleCount(circleMapper.selectCount(null));
        return vo;
    }
}

