package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {

}
