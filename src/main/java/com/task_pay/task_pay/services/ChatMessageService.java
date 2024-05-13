package com.task_pay.task_pay.services;

import com.task_pay.task_pay.models.entities.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findChatMessages(Integer senderId, Integer recipientId);
}
