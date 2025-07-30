package com.watches.backend.controller;

import com.watches.backend.model.ChatMessage;
import com.watches.backend.Dto.MessageDto.SendMessageDTO;
import com.watches.backend.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Send a new message
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody SendMessageDTO dto) {
        ChatMessage savedMessage = chatMessageService.saveMessage(dto);
        return ResponseEntity.ok(savedMessage);
    }

    // Get chat history between two users
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id
    ) {
        List<ChatMessage> chatHistory = chatMessageService.getChatHistory(user1Id, user2Id);
        return ResponseEntity.ok(chatHistory);
    }
}
