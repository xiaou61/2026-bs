package com.folksong.platform.service;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.CommentDTO;
import com.folksong.platform.vo.CommentVO;

public interface CommentService {
    PageResult<CommentVO> getCommentsBySongId(Long songId, Integer pageNum, Integer pageSize);
    void createComment(Long userId, CommentDTO dto);
    void deleteComment(Long userId, Long commentId);
    PageResult<CommentVO> getAllComments(Integer pageNum, Integer pageSize);
    void deleteCommentByAdmin(Long commentId);
}
