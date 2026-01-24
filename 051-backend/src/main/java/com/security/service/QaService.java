package com.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.dto.QaPostDTO;
import com.security.dto.QaReplyDTO;
import com.security.vo.QaPostVO;

public interface QaService {
    Page<QaPostVO> getPostList(Integer page, Integer size);
    QaPostVO getPostDetail(Long id);
    void createPost(QaPostDTO dto, Long userId);
    void createReply(QaReplyDTO dto, Long userId);
    void likeReply(Long replyId, Long userId);
}
