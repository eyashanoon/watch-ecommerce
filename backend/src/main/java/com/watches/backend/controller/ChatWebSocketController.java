package com.watches.backend.controller;

import com.watches.backend.Dto.MessageDto.ChatMessageDTO;
import com.watches.backend.Dto.MessageDto.SendMessageDTO;
import com.watches.backend.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(ChatMessageService chatMessageService,
                                   SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    public void send(SendMessageDTO dto) {
        System.out.println(dto.getContent() + "/" + dto.getRecipientId() + "/" + dto.getSenderId());

        ChatMessageDTO savedMessage = chatMessageService.saveMessage(dto);

        // Send to recipient
        messagingTemplate.convertAndSendToUser(
                String.valueOf(dto.getRecipientId()), // user identifier
                "/queue/messages",                    // endpoint
                savedMessage
        );

        // Optionally send back to sender (to show it in their chat view)
        messagingTemplate.convertAndSendToUser(
                String.valueOf(dto.getSenderId()),
                "/queue/messages",
                savedMessage
        );
    }
}
