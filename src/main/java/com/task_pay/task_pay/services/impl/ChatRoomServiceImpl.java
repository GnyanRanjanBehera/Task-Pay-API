package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.models.entities.ChatRoom;
import com.task_pay.task_pay.repositories.ChatRoomRepository;
import com.task_pay.task_pay.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChatRoomServiceImpl implements ChatRoomService {


    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Override
    public Optional<Integer> getChatRoomId(Integer senderId, Integer recipientId, boolean createNewRoomIfNotExists) {
        {
            return chatRoomRepository
                    .findBySenderIdAndRecipientId(senderId, recipientId)
                    .map(ChatRoom::getChatId)
                    .or(() -> {
                        if (createNewRoomIfNotExists) {
                            var chatId = createChatId(senderId, recipientId);
                            return Optional.of(chatId);
                        }

                        return Optional.empty();
                    });
        }
    }

        private Integer createChatId (Integer senderId, Integer recipientId){
            var chatId = Integer.parseInt(String.format("%d%d", senderId, recipientId));
            ChatRoom senderRecipient = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            ChatRoom recipientSender = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(recipientId)
                    .recipientId(senderId)
                    .build();

            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

            return chatId;
        }


}

