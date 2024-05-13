package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByChatId(Integer chatId);
}
