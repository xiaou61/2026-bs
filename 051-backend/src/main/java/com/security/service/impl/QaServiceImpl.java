package com.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.BusinessException;
import com.security.dto.QaPostDTO;
import com.security.dto.QaReplyDTO;
import com.security.entity.QaPost;
import com.security.entity.QaReply;
import com.security.entity.User;
import com.security.mapper.QaPostMapper;
import com.security.mapper.QaReplyMapper;
import com.security.mapper.UserMapper;
import com.security.service.QaService;
import com.security.vo.QaPostVO;
import com.security.vo.QaReplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QaServiceImpl implements QaService {

    @Autowired
    private QaPostMapper qaPostMapper;

    @Autowired
    private QaReplyMapper qaReplyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Page<QaPostVO> getPostList(Integer page, Integer size) {
        Page<QaPost> postPage = new Page<>(page, size);
        qaPostMapper.selectPage(postPage,
                new LambdaQueryWrapper<QaPost>()
                        .eq(QaPost::getStatus, 1)
                        .orderByDesc(QaPost::getCreateTime));

        Page<QaPostVO> voPage = new Page<>(page, size, postPage.getTotal());
        List<QaPostVO> voList = postPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public QaPostVO getPostDetail(Long id) {
        QaPost post = qaPostMapper.selectById(id);
        if (post == null) {
            throw new BusinessException("问答不存在");
        }

        post.setViewCount(post.getViewCount() + 1);
        qaPostMapper.updateById(post);

        QaPostVO vo = convertToVO(post);

        List<QaReply> replies = qaReplyMapper.selectList(
                new LambdaQueryWrapper<QaReply>()
                        .eq(QaReply::getPostId, id)
                        .orderByDesc(QaReply::getCreateTime));

        List<QaReplyVO> replyVOs = replies.stream()
                .map(reply -> {
                    QaReplyVO replyVO = new QaReplyVO();
                    BeanUtils.copyProperties(reply, replyVO);
                    User user = userMapper.selectById(reply.getUserId());
                    if (user != null) {
                        replyVO.setNickname(user.getNickname());
                        replyVO.setAvatar(user.getAvatar());
                    }
                    return replyVO;
                })
                .collect(Collectors.toList());
        vo.setReplies(replyVOs);

        return vo;
    }

    @Override
    public void createPost(QaPostDTO dto, Long userId) {
        QaPost post = new QaPost();
        post.setUserId(userId);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setViewCount(0);
        post.setReplyCount(0);
        post.setStatus(1);
        qaPostMapper.insert(post);
    }

    @Override
    public void createReply(QaReplyDTO dto, Long userId) {
        QaPost post = qaPostMapper.selectById(dto.getPostId());
        if (post == null) {
            throw new BusinessException("问答不存在");
        }

        QaReply reply = new QaReply();
        reply.setPostId(dto.getPostId());
        reply.setUserId(userId);
        reply.setContent(dto.getContent());
        reply.setLikeCount(0);
        qaReplyMapper.insert(reply);

        post.setReplyCount(post.getReplyCount() + 1);
        qaPostMapper.updateById(post);
    }

    @Override
    public void likeReply(Long replyId, Long userId) {
        String likeKey = "reply:like:" + replyId + ":" + userId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(likeKey))) {
            throw new BusinessException("已经点赞过了");
        }
        redisTemplate.opsForValue().set(likeKey, "1", 365, TimeUnit.DAYS);

        QaReply reply = qaReplyMapper.selectById(replyId);
        if (reply != null) {
            reply.setLikeCount(reply.getLikeCount() + 1);
            qaReplyMapper.updateById(reply);
        }
    }

    private QaPostVO convertToVO(QaPost post) {
        QaPostVO vo = new QaPostVO();
        BeanUtils.copyProperties(post, vo);
        User user = userMapper.selectById(post.getUserId());
        if (user != null) {
            vo.setNickname(user.getNickname());
            vo.setAvatar(user.getAvatar());
        }
        return vo;
    }
}
