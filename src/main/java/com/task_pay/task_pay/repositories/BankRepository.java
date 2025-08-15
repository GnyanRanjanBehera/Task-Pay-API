package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank,Integer> {
    @Query(value = "SELECT * FROM bank b WHERE b.user_id = :userId", nativeQuery = true)
    List<Bank> findBanks(@Param("userId") Integer userId);


}
