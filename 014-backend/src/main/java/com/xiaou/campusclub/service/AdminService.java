package com.xiaou.campusclub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.entity.Club;
import com.xiaou.campusclub.entity.User;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.StatisticsVO;
import com.xiaou.campusclub.vo.TopicVO;

public interface AdminService {
    LoginVO login(LoginRequest request);
    IPage<User> getUserList(Integer page, Integer size, String keyword);
    void updateUserStatus(Long userId, Integer status);
    IPage<Club> getPendingClubs(Integer page, Integer size);
    void approveClub(Long clubId, Integer status);
    IPage<TopicVO> getTopicList(Integer page, Integer size);
    void topTopic(Long topicId, Integer isTop);
    void deleteTopic(Long topicId);
    StatisticsVO getStatistics();
}

