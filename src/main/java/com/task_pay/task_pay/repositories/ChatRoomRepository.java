package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Integer> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(Integer senderId, Integer recipientId);
}
