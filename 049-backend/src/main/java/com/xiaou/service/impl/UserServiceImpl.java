package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.JwtUtil;
import com.xiaou.dto.LoginDTO;
import com.xiaou.dto.RegisterDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtUtil jwtUtil;
    private final StudyRecordMapper studyRecordMapper;
    private final AnswerRecordMapper answerRecordMapper;
    private final DailyCheckinMapper dailyCheckinMapper;

    @Override
    public Map<String, Object> login(LoginDTO dto) {
        User user = getByUsername(dto.getUsername());
        if (user == null || !user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已被禁用");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    @Override
    public void register(RegisterDTO dto) {
        if (getByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setTargetSchool(dto.getTargetSchool());
        user.setTargetMajor(dto.getTargetMajor());
        user.setExamYear(dto.getExamYear());
        user.setRole(0);
        user.setStatus(1);
        user.setPoints(0);
        user.setStudyDays(0);
        save(user);
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public Page<User> getPage(int current, int size, String keyword, Integer role) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(User::getUsername, keyword).or().like(User::getNickname, keyword));
        }
        if (role != null) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public void updateProfile(User user) {
        updateById(user);
    }

    @Override
    public Map<String, Object> getStudyStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        // 学习总时长
        Long totalDuration = studyRecordMapper.selectList(
                new LambdaQueryWrapper<StudyRecord>().eq(StudyRecord::getUserId, userId)
        ).stream().mapToLong(r -> r.getStudyDuration() != null ? r.getStudyDuration() : 0).sum();
        stats.put("totalStudyDuration", totalDuration / 3600); // 小时
        
        // 做题总数
        Long totalQuestions = answerRecordMapper.selectCount(
                new LambdaQueryWrapper<AnswerRecord>().eq(AnswerRecord::getUserId, userId));
        stats.put("totalQuestions", totalQuestions);
        
        // 正确率
        Long correctCount = answerRecordMapper.selectCount(
                new LambdaQueryWrapper<AnswerRecord>().eq(AnswerRecord::getUserId, userId).eq(AnswerRecord::getIsCorrect, 1));
        stats.put("correctRate", totalQuestions > 0 ? correctCount * 100 / totalQuestions : 0);
        
        // 今日是否打卡
        Long todayCheckin = dailyCheckinMapper.selectCount(
                new LambdaQueryWrapper<DailyCheckin>().eq(DailyCheckin::getUserId, userId).eq(DailyCheckin::getCheckinDate, LocalDate.now()));
        stats.put("todayChecked", todayCheckin > 0);
        
        return stats;
    }
}
