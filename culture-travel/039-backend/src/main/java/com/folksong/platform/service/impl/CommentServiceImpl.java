package com.folksong.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.CommentDTO;
import com.folksong.platform.entity.Comment;
import com.folksong.platform.exception.BusinessException;
import com.folksong.platform.repository.CommentRepository;
import com.folksong.platform.repository.FolkSongRepository;
import com.folksong.platform.repository.UserRepository;
import com.folksong.platform.service.CommentService;
import com.folksong.platform.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final FolkSongRepository folkSongRepository;
    private final UserRepository userRepository;

    @Override
    public PageResult<CommentVO> getCommentsBySongId(Long songId, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Comment> rootComments = commentRepository.findRootCommentsBySongId(songId, pageSize, offset);
        Long total = commentRepository.countBySongId(songId);
        List<CommentVO> voList = rootComments.stream().map(this::convertToVOWithReplies).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    @Transactional
    public void createComment(Long userId, CommentDTO dto) {
        Comment comment = new Comment();
        comment.setSongId(dto.getSongId());
        comment.setUserId(userId);
        comment.setParentId(dto.getParentId());
        comment.setContent(dto.getContent());
        comment.setLikeCount(0);
        comment.setStatus(1);
        comment.setCreateTime(LocalDateTime.now());
        commentRepository.save(comment);
        folkSongRepository.updateCommentCount(dto.getSongId(), 1);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("评论不存在"));
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权删除他人评论");
        }
        commentRepository.deleteById(commentId);
        folkSongRepository.updateCommentCount(comment.getSongId(), -1);
    }

    @Override
    public PageResult<CommentVO> getAllComments(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Comment> comments = commentRepository.findAllWithPage(pageSize, offset);
        Long total = commentRepository.count();
        List<CommentVO> voList = comments.stream().map(this::convertToVO).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("评论不存在"));
        commentRepository.deleteById(commentId);
        folkSongRepository.updateCommentCount(comment.getSongId(), -1);
    }

    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtil.copyProperties(comment, vo);
        userRepository.findById(comment.getUserId()).ifPresent(u -> {
            vo.setUserName(u.getNickname());
            vo.setUserAvatar(u.getAvatar());
        });
        return vo;
    }

    private CommentVO convertToVOWithReplies(Comment comment) {
        CommentVO vo = convertToVO(comment);
        List<Comment> replies = commentRepository.findRepliesByParentId(comment.getId());
        vo.setReplies(replies.stream().map(this::convertToVO).collect(Collectors.toList()));
        return vo;
    }
}
