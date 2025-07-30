package com.watches.backend.controller;


import com.watches.backend.Dto.MessageDto.SendMessageDTO;
import com.watches.backend.mappers.ChatMessageMapper;
import com.watches.backend.model.ChatMessage;
import com.watches.backend.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;

    public ChatWebSocketController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Client sends to /app/chat
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage send(SendMessageDTO dto) {
        return chatMessageService.saveMessage(dto);
    }
}
