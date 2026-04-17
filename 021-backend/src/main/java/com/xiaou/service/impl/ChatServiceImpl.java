package com.xiaou.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.dto.BargainDTO;
import com.xiaou.dto.ChatReadDTO;
import com.xiaou.dto.ChatSendDTO;
import com.xiaou.entity.ChatMessage;
import com.xiaou.entity.OrderInfo;
import com.xiaou.entity.Product;
import com.xiaou.entity.User;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.ChatMessageMapper;
import com.xiaou.mapper.OrderInfoMapper;
import com.xiaou.mapper.ProductMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.ChatService;
import com.xiaou.vo.ChatMessageVO;
import com.xiaou.vo.ChatSessionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageMapper chatMessageMapper;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final OrderInfoMapper orderInfoMapper;

    @Override
    @Transactional
    public Long sendMessage(Long userId, ChatSendDTO sendDTO) {
        Product product = getProduct(sendDTO.getProductId());
        getUser(userId);
        getUser(sendDTO.getReceiverId());
        validateConversation(product, userId, sendDTO.getReceiverId());

        ChatMessage message = new ChatMessage();
        message.setProductId(sendDTO.getProductId());
        message.setSenderId(userId);
        message.setReceiverId(sendDTO.getReceiverId());
        message.setContent(sendDTO.getContent());
        message.setIsRead(0);
        message.setMessageType("text");
        chatMessageMapper.insert(message);
        return message.getId();
    }

    @Override
    public List<ChatSessionVO> getChatList(Long userId) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(q -> q.eq(ChatMessage::getSenderId, userId).or().eq(ChatMessage::getReceiverId, userId))
                .orderByDesc(ChatMessage::getCreateTime);
        List<ChatMessage> messages = chatMessageMapper.selectList(wrapper);

        Map<String, ChatSessionVO> sessions = new LinkedHashMap<>();
        for (ChatMessage message : messages) {
            Long targetUserId = message.getSenderId().equals(userId) ? message.getReceiverId() : message.getSenderId();
            String key = message.getProductId() + "_" + targetUserId;
            ChatSessionVO session = sessions.get(key);
            if (session == null) {
                Product product = getProduct(message.getProductId());
                User targetUser = getUser(targetUserId);
                session = new ChatSessionVO();
                session.setProductId(product.getId());
                session.setProductTitle(product.getTitle());
                session.setProductImages(product.getImages());
                session.setTargetUserId(targetUserId);
                session.setTargetUserName(targetUser.getRealName());
                session.setTargetUserAvatar(targetUser.getAvatar());
                session.setLastMessage(message.getContent());
                session.setLastMessageType(message.getMessageType());
                session.setBargainPrice(message.getBargainPrice());
                session.setBargainStatus(message.getBargainStatus());
                session.setLastTime(message.getCreateTime());
                session.setUnreadCount(0);
                sessions.put(key, session);
            }

            if (message.getReceiverId().equals(userId) && message.getIsRead() == 0) {
                session.setUnreadCount(session.getUnreadCount() + 1);
            }
        }
        return sessions.values().stream()
                .sorted((a, b) -> b.getLastTime().compareTo(a.getLastTime()))
                .toList();
    }

    @Override
    public List<ChatMessageVO> getMessages(Long userId, Long targetUserId, Long productId) {
        Product product = getProduct(productId);
        validateConversation(product, userId, targetUserId);

        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getProductId, productId)
                .and(q -> q.and(c -> c.eq(ChatMessage::getSenderId, userId).eq(ChatMessage::getReceiverId, targetUserId))
                        .or()
                        .and(c -> c.eq(ChatMessage::getSenderId, targetUserId).eq(ChatMessage::getReceiverId, userId)))
                .orderByAsc(ChatMessage::getCreateTime);
        return chatMessageMapper.selectList(wrapper).stream().map(this::toChatMessageVO).toList();
    }

    @Override
    @Transactional
    public void markRead(Long userId, ChatReadDTO readDTO) {
        Product product = getProduct(readDTO.getProductId());
        validateConversation(product, userId, readDTO.getTargetUserId());

        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getProductId, readDTO.getProductId())
                .eq(ChatMessage::getSenderId, readDTO.getTargetUserId())
                .eq(ChatMessage::getReceiverId, userId)
                .eq(ChatMessage::getIsRead, 0);
        List<ChatMessage> unreadMessages = chatMessageMapper.selectList(wrapper);
        for (ChatMessage unreadMessage : unreadMessages) {
            unreadMessage.setIsRead(1);
            chatMessageMapper.updateById(unreadMessage);
        }
    }

    @Override
    @Transactional
    public Long bargain(Long userId, BargainDTO bargainDTO) {
        Product product = getProduct(bargainDTO.getProductId());
        validateConversation(product, userId, bargainDTO.getReceiverId());

        ChatMessage message = new ChatMessage();
        message.setProductId(bargainDTO.getProductId());
        message.setSenderId(userId);
        message.setReceiverId(bargainDTO.getReceiverId());
        message.setContent((bargainDTO.getContent() == null || bargainDTO.getContent().isBlank()) ? "发起议价" : bargainDTO.getContent());
        message.setIsRead(0);
        message.setMessageType("bargain");
        message.setBargainPrice(bargainDTO.getBargainPrice());
        message.setBargainStatus("pending");
        chatMessageMapper.insert(message);
        return message.getId();
    }

    @Override
    @Transactional
    public void acceptBargain(Long userId, Long messageId) {
        ChatMessage message = getPendingBargainMessage(messageId, userId);
        Product product = getProduct(message.getProductId());
        validateBargainAccept(product);

        message.setBargainStatus("accepted");
        chatMessageMapper.updateById(message);

        product.setPrice(message.getBargainPrice());
        product.setStatus("sold");
        productMapper.updateById(product);

        createBargainOrder(product, message);
    }

    @Override
    @Transactional
    public void rejectBargain(Long userId, Long messageId) {
        ChatMessage message = getPendingBargainMessage(messageId, userId);
        message.setBargainStatus("rejected");
        chatMessageMapper.updateById(message);
    }

    private ChatMessage getPendingBargainMessage(Long messageId, Long userId) {
        ChatMessage message = chatMessageMapper.selectById(messageId);
        if (message == null || !"bargain".equals(message.getMessageType())) {
            throw new BusinessException("议价消息不存在");
        }
        if (!message.getReceiverId().equals(userId)) {
            throw new BusinessException("无权限处理该议价");
        }
        if (!"pending".equals(message.getBargainStatus())) {
            throw new BusinessException("该议价已处理");
        }
        return message;
    }

    private void createBargainOrder(Product product, ChatMessage message) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        orderInfo.setProductId(product.getId());
        orderInfo.setSellerId(product.getSellerId());
        orderInfo.setBuyerId(message.getSenderId());
        orderInfo.setPrice(message.getBargainPrice());
        orderInfo.setOriginalPrice(product.getOriginalPrice() == null ? product.getPrice() : product.getOriginalPrice());
        orderInfo.setIsBargain(1);
        orderInfo.setStatus("pending");
        orderInfoMapper.insert(orderInfo);
    }

    private ChatMessageVO toChatMessageVO(ChatMessage message) {
        User sender = getUser(message.getSenderId());
        User receiver = getUser(message.getReceiverId());

        ChatMessageVO messageVO = new ChatMessageVO();
        messageVO.setId(message.getId());
        messageVO.setProductId(message.getProductId());
        messageVO.setSenderId(message.getSenderId());
        messageVO.setSenderName(sender.getRealName());
        messageVO.setSenderAvatar(sender.getAvatar());
        messageVO.setReceiverId(message.getReceiverId());
        messageVO.setReceiverName(receiver.getRealName());
        messageVO.setReceiverAvatar(receiver.getAvatar());
        messageVO.setContent(message.getContent());
        messageVO.setIsRead(message.getIsRead());
        messageVO.setMessageType(message.getMessageType());
        messageVO.setBargainPrice(message.getBargainPrice());
        messageVO.setBargainStatus(message.getBargainStatus());
        messageVO.setCreateTime(message.getCreateTime());
        return messageVO;
    }

    private Product getProduct(Long productId) {
        Product product = productMapper.selectById(productId);
        if (product == null || product.getDeleted() == 1) {
            throw new BusinessException("商品不存在");
        }
        return product;
    }

    private User getUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    private void validateConversation(Product product, Long currentUserId, Long targetUserId) {
        if (currentUserId.equals(targetUserId)) {
            throw new BusinessException("不能给自己发送消息");
        }
        if (!product.getSellerId().equals(currentUserId) && !product.getSellerId().equals(targetUserId)) {
            throw new BusinessException("当前商品会话必须包含卖家");
        }
    }

    private void validateBargainAccept(Product product) {
        if (!"on_sale".equals(product.getStatus())) {
            throw new BusinessException("商品当前不可接受议价");
        }

        LambdaQueryWrapper<OrderInfo> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(OrderInfo::getProductId, product.getId())
                .in(OrderInfo::getStatus, "pending", "completed");
        if (orderInfoMapper.selectCount(orderWrapper) > 0) {
            throw new BusinessException("商品已生成有效订单");
        }
    }
}
