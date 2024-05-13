package com.task_pay.task_pay.services.impl;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.entities.ChatMessage;
import com.task_pay.task_pay.repositories.ChatMessageRepository;
import com.task_pay.task_pay.services.ChatMessageService;
import com.task_pay.task_pay.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ChatMessageServiceImpl implements ChatMessageService {


    @Autowired
    private ChatMessageRepository repository;

    @Autowired
    private ChatRoomService chatRoomService;
    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(() -> new ResourceNotFoundException("chat room id not found !"));
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }


    @Override
    public List<ChatMessage> findChatMessages(Integer senderId, Integer recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
