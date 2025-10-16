package com.xiaou.campusclub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.dto.ClubRequest;
import com.xiaou.campusclub.entity.Club;
import com.xiaou.campusclub.vo.ClubVO;

import java.util.List;

public interface ClubService {
    IPage<ClubVO> getClubList(Integer page, Integer size, String category, String keyword, Long userId);
    ClubVO getClubDetail(Long clubId, Long userId);
    Long createClub(Long userId, ClubRequest request);
    void updateClub(Long clubId, Long userId, ClubRequest request);
    void joinClub(Long clubId, Long userId);
    void approveMember(Long clubId, Long userId, Long memberId, Integer status);
    List<ClubVO> getMyClubs(Long userId);
    void updateRecruitStatus(Long clubId, Long userId, Integer isRecruiting, String recruitInfo);
}

