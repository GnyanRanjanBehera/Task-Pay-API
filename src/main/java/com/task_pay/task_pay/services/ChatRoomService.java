package com.task_pay.task_pay.services;

import java.util.Optional;

public interface ChatRoomService {

    Optional<Integer> getChatRoomId(Integer senderId,Integer recipientId, boolean createNewRoomIfNotExists);
}
