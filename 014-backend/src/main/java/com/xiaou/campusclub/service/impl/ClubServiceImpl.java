package com.xiaou.campusclub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusclub.dto.ClubRequest;
import com.xiaou.campusclub.entity.Club;
import com.xiaou.campusclub.entity.ClubMember;
import com.xiaou.campusclub.entity.User;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.ClubMapper;
import com.xiaou.campusclub.mapper.ClubMemberMapper;
import com.xiaou.campusclub.mapper.UserMapper;
import com.xiaou.campusclub.service.ClubService;
import com.xiaou.campusclub.service.UserService;
import com.xiaou.campusclub.vo.ClubVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    
    @Autowired
    private ClubMapper clubMapper;
    
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    public IPage<ClubVO> getClubList(Integer page, Integer size, String category, String keyword, Long userId) {
        Page<Club> clubPage = new Page<>(page, size);
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getStatus, 1);
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Club::getCategory, category);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Club::getName, keyword)
                   .or()
                   .like(Club::getDescription, keyword);
        }
        
        wrapper.orderByDesc(Club::getCreateTime);
        
        IPage<Club> result = clubMapper.selectPage(clubPage, wrapper);
        
        return result.convert(club -> convertToVO(club, userId));
    }
    
    @Override
    public ClubVO getClubDetail(Long clubId, Long userId) {
        Club club = clubMapper.selectById(clubId);
        if (club == null) {
            throw new BusinessException("社团不存在");
        }
        
        return convertToVO(club, userId);
    }
    
    @Override
    @Transactional
    public Long createClub(Long userId, ClubRequest request) {
        Club club = new Club();
        club.setName(request.getName());
        club.setCategory(request.getCategory());
        club.setLogo(request.getLogo());
        club.setCover(request.getCover());
        club.setDescription(request.getDescription());
        club.setPresidentId(userId);
        club.setMemberCount(1);
        club.setMaxMembers(request.getMaxMembers() != null ? request.getMaxMembers() : 100);
        club.setStatus(0);
        club.setIsRecruiting(0);
        club.setRecruitInfo(request.getRecruitInfo());
        club.setCreateTime(LocalDateTime.now());
        
        clubMapper.insert(club);
        
        ClubMember member = new ClubMember();
        member.setClubId(club.getId());
        member.setUserId(userId);
        member.setRole(2);
        member.setStatus(1);
        member.setJoinTime(LocalDateTime.now());
        clubMemberMapper.insert(member);
        
        return club.getId();
    }
    
    @Override
    @Transactional
    public void updateClub(Long clubId, Long userId, ClubRequest request) {
        Club club = clubMapper.selectById(clubId);
        if (club == null) {
            throw new BusinessException("社团不存在");
        }
        
        if (!isClubAdmin(clubId, userId)) {
            throw new BusinessException("无权限操作");
        }
        
        club.setName(request.getName());
        club.setCategory(request.getCategory());
        club.setLogo(request.getLogo());
        club.setCover(request.getCover());
        club.setDescription(request.getDescription());
        club.setUpdateTime(LocalDateTime.now());
        
        clubMapper.updateById(club);
    }
    
    @Override
    @Transactional
    public void joinClub(Long clubId, Long userId) {
        Club club = clubMapper.selectById(clubId);
        if (club == null) {
            throw new BusinessException("社团不存在");
        }
        
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getClubId, clubId)
               .eq(ClubMember::getUserId, userId);
        ClubMember existMember = clubMemberMapper.selectOne(wrapper);
        
        if (existMember != null && existMember.getStatus() == 1) {
            throw new BusinessException("已是社团成员");
        }
        
        if (existMember != null && existMember.getStatus() == 0) {
            throw new BusinessException("申请审核中");
        }
        
        ClubMember member = new ClubMember();
        member.setClubId(clubId);
        member.setUserId(userId);
        member.setRole(0);
        member.setStatus(0);
        member.setJoinTime(LocalDateTime.now());
        clubMemberMapper.insert(member);
    }
    
    @Override
    @Transactional
    public void approveMember(Long clubId, Long userId, Long memberId, Integer status) {
        if (!isClubAdmin(clubId, userId)) {
            throw new BusinessException("无权限操作");
        }
        
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getClubId, clubId)
               .eq(ClubMember::getUserId, memberId);
        ClubMember member = clubMemberMapper.selectOne(wrapper);
        
        if (member == null) {
            throw new BusinessException("申请不存在");
        }
        
        member.setStatus(status);
        clubMemberMapper.updateById(member);
        
        if (status == 1) {
            Club club = clubMapper.selectById(clubId);
            club.setMemberCount(club.getMemberCount() + 1);
            clubMapper.updateById(club);
            
            userService.addPoints(memberId, 5, "JOIN_CLUB", "加入社团：" + club.getName());
        }
    }
    
    @Override
    public List<ClubVO> getMyClubs(Long userId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getUserId, userId)
               .eq(ClubMember::getStatus, 1);
        List<ClubMember> members = clubMemberMapper.selectList(wrapper);
        
        return members.stream()
                .map(member -> {
                    Club club = clubMapper.selectById(member.getClubId());
                    return club != null ? convertToVO(club, userId) : null;
                })
                .filter(vo -> vo != null)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void updateRecruitStatus(Long clubId, Long userId, Integer isRecruiting, String recruitInfo) {
        if (!isClubAdmin(clubId, userId)) {
            throw new BusinessException("无权限操作");
        }
        
        Club club = clubMapper.selectById(clubId);
        if (club == null) {
            throw new BusinessException("社团不存在");
        }
        
        club.setIsRecruiting(isRecruiting);
        club.setRecruitInfo(recruitInfo);
        club.setUpdateTime(LocalDateTime.now());
        clubMapper.updateById(club);
    }
    
    private boolean isClubAdmin(Long clubId, Long userId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getClubId, clubId)
               .eq(ClubMember::getUserId, userId)
               .in(ClubMember::getRole, 1, 2)
               .eq(ClubMember::getStatus, 1);
        return clubMemberMapper.selectCount(wrapper) > 0;
    }
    
    private ClubVO convertToVO(Club club, Long userId) {
        ClubVO vo = BeanUtil.copyProperties(club, ClubVO.class);
        
        User president = userMapper.selectById(club.getPresidentId());
        if (president != null) {
            vo.setPresidentName(president.getRealName());
        }
        
        if (userId != null) {
            LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ClubMember::getClubId, club.getId())
                   .eq(ClubMember::getUserId, userId)
                   .eq(ClubMember::getStatus, 1);
            vo.setIsMember(clubMemberMapper.selectCount(wrapper) > 0);
        }
        
        return vo;
    }
}

