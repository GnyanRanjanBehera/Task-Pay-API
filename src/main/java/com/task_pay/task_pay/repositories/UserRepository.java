package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByInvitationCode(String invitationCode);
    Optional<User> findByMobileNumberAndInvitationCode(String mobileNumber, String invitationCode);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword% OR u.mobileNumber LIKE %:keyword% OR u.email LIKE %:keyword%")
    Page<Objects[]> findByKeyword(String keyword, Pageable pageable);

}
