package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank,Integer> {
    Optional<Bank> findByUser_UserId(Integer userId);

}
