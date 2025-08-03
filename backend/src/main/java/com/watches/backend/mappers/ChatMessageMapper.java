package com.watches.backend.mappers;

import com.watches.backend.Dto.MessageDto.ChatMessageDTO;
import com.watches.backend.Dto.MessageDto.SendMessageDTO;
import com.watches.backend.model.ChatMessage;
import com.watches.backend.model.User;

import java.time.LocalDateTime;

public class ChatMessageMapper {
    public static ChatMessageDTO toDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setRecipientId(message.getRecipient().getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }

    public static ChatMessage toEntity(SendMessageDTO dto, User sender, User recipient) {
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(dto.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
