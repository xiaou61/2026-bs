package com.xiaou.service;

import com.xiaou.dto.BargainDTO;
import com.xiaou.dto.ChatReadDTO;
import com.xiaou.dto.ChatSendDTO;
import com.xiaou.vo.ChatMessageVO;
import com.xiaou.vo.ChatSessionVO;

import java.util.List;

public interface ChatService {

    Long sendMessage(Long userId, ChatSendDTO sendDTO);

    List<ChatSessionVO> getChatList(Long userId);

    List<ChatMessageVO> getMessages(Long userId, Long targetUserId, Long productId);

    void markRead(Long userId, ChatReadDTO readDTO);

    Long bargain(Long userId, BargainDTO bargainDTO);

    void acceptBargain(Long userId, Long messageId);

    void rejectBargain(Long userId, Long messageId);
}
