package com.security.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.common.BusinessException;
import com.security.dto.LoginDTO;
import com.security.dto.UserUpdateDTO;
import com.security.entity.User;
import com.security.mapper.*;
import com.security.service.UserService;
import com.security.utils.JwtUtils;
import com.security.vo.LoginVO;
import com.security.vo.UserStatsVO;
import com.security.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LearnRecordMapper learnRecordMapper;

    @Autowired
    private AnswerRecordMapper answerRecordMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public LoginVO login(LoginDTO dto) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret + "&js_code=" + dto.getCode() + "&grant_type=authorization_code";
        String result = HttpUtil.get(url);
        JSONObject json = JSONUtil.parseObj(result);
        String openid = json.getStr("openid");
        if (openid == null) {
            throw new BusinessException("微信登录失败");
        }

        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getOpenid, openid));
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setNickname(dto.getNickname() != null ? dto.getNickname() : "用户" + System.currentTimeMillis());
            user.setAvatar(dto.getAvatar());
            user.setPoints(0);
            user.setLevel(1);
            this.save(user);
        }

        String token = jwtUtils.generateToken(user.getId(), "user");
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        return vo;
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setPoints(user.getPoints());
        vo.setLevel(user.getLevel());
        vo.setLevelName(getLevelName(user.getLevel()));
        return vo;
    }

    @Override
    public void updateUserInfo(Long userId, UserUpdateDTO dto) {
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        this.updateById(user);
    }

    @Override
    public UserStatsVO getUserStats(Long userId) {
        UserStatsVO vo = new UserStatsVO();
        User user = this.getById(userId);
        vo.setPoints(user.getPoints());
        vo.setLevel(user.getLevel());

        Long learnedCount = learnRecordMapper.selectCount(
                new LambdaQueryWrapper<com.security.entity.LearnRecord>().eq(com.security.entity.LearnRecord::getUserId, userId));
        vo.setLearnedCount(learnedCount.intValue());

        Long questionCount = answerRecordMapper.selectCount(
                new LambdaQueryWrapper<com.security.entity.AnswerRecord>().eq(com.security.entity.AnswerRecord::getUserId, userId));
        vo.setQuestionCount(questionCount.intValue());

        Long correctCount = answerRecordMapper.selectCount(
                new LambdaQueryWrapper<com.security.entity.AnswerRecord>()
                        .eq(com.security.entity.AnswerRecord::getUserId, userId)
                        .eq(com.security.entity.AnswerRecord::getIsCorrect, 1));
        vo.setCorrectCount(correctCount.intValue());

        Long favoriteCount = favoriteMapper.selectCount(
                new LambdaQueryWrapper<com.security.entity.Favorite>().eq(com.security.entity.Favorite::getUserId, userId));
        vo.setFavoriteCount(favoriteCount.intValue());

        Long rank = baseMapper.selectCount(new LambdaQueryWrapper<User>().gt(User::getPoints, user.getPoints())) + 1;
        vo.setRank(rank.intValue());

        return vo;
    }

    @Override
    public void addPoints(Long userId, Integer points) {
        User user = this.getById(userId);
        user.setPoints(user.getPoints() + points);
        int newLevel = calculateLevel(user.getPoints());
        user.setLevel(newLevel);
        this.updateById(user);
    }

    private int calculateLevel(int points) {
        if (points >= 5000) return 5;
        if (points >= 2000) return 4;
        if (points >= 500) return 3;
        if (points >= 100) return 2;
        return 1;
    }

    private String getLevelName(int level) {
        switch (level) {
            case 1: return "安全小白";
            case 2: return "安全入门";
            case 3: return "安全达人";
            case 4: return "安全专家";
            case 5: return "安全大师";
            default: return "安全小白";
        }
    }
}
