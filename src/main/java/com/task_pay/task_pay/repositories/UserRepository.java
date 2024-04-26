package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmail(String email);

}
