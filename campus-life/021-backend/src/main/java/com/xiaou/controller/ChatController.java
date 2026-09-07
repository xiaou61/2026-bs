package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.BargainDTO;
import com.xiaou.dto.ChatReadDTO;
import com.xiaou.dto.ChatSendDTO;
import com.xiaou.service.ChatService;
import com.xiaou.vo.ChatMessageVO;
import com.xiaou.vo.ChatSessionVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    public Result<Long> sendMessage(@RequestAttribute("userId") Long userId,
                                    @Valid @RequestBody ChatSendDTO sendDTO) {
        return Result.success(chatService.sendMessage(userId, sendDTO));
    }

    @GetMapping("/list")
    public Result<List<ChatSessionVO>> getChatList(@RequestAttribute("userId") Long userId) {
        return Result.success(chatService.getChatList(userId));
    }

    @GetMapping("/messages")
    public Result<List<ChatMessageVO>> getMessages(@RequestAttribute("userId") Long userId,
                                                   @RequestParam Long targetUserId,
                                                   @RequestParam Long productId) {
        return Result.success(chatService.getMessages(userId, targetUserId, productId));
    }

    @PutMapping("/read")
    public Result<String> markRead(@RequestAttribute("userId") Long userId,
                                   @Valid @RequestBody ChatReadDTO readDTO) {
        chatService.markRead(userId, readDTO);
        return Result.success("已读状态更新成功");
    }

    @PostMapping("/bargain")
    public Result<Long> bargain(@RequestAttribute("userId") Long userId,
                                @Valid @RequestBody BargainDTO bargainDTO) {
        return Result.success(chatService.bargain(userId, bargainDTO));
    }

    @PutMapping("/bargain/{id}/accept")
    public Result<String> acceptBargain(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id) {
        chatService.acceptBargain(userId, id);
        return Result.success("议价已接受");
    }

    @PutMapping("/bargain/{id}/reject")
    public Result<String> rejectBargain(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id) {
        chatService.rejectBargain(userId, id);
        return Result.success("议价已拒绝");
    }
}
