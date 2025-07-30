package com.watches.backend.service;

import com.watches.backend.model.ChatMessage;
import com.watches.backend.model.User;
import com.watches.backend.Repositories.ChatMessageRepository;
import com.watches.backend.Repositories.UserRepository;
import com.watches.backend.Dto.MessageDto.SendMessageDTO;
import com.watches.backend.mappers.ChatMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    // Save new message
    public ChatMessage saveMessage(SendMessageDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        ChatMessage message = ChatMessageMapper.toEntity(dto, sender, recipient);
        return chatMessageRepository.save(message);
    }

    // Get all messages between two users
    public List<ChatMessage> getChatHistory(Long user1Id, Long user2Id) {
        return chatMessageRepository.findChatHistory(user1Id, user2Id);
    }
}
