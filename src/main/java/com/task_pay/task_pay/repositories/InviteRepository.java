package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.Invite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Objects;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    @Query("SELECT i FROM Invite i WHERE i.invitedUser.userId = :invitedUserId")
    Optional<Invite> findByInvitedUserId(Integer invitedUserId);
    @Query("SELECT i FROM Invite i WHERE i.inviteUser.userId = :userId")
    Page<Objects[]> findInvitedUsersByUserId(Integer userId, Pageable pageable);
}
