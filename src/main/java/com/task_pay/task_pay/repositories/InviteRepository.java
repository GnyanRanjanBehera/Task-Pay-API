package com.task_pay.task_pay.repositories;

import com.task_pay.task_pay.models.entities.Invite;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.utils.response.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

//    @Query("SELECT i.user FROM Invite i WHERE i.inviteUserId = :userId")
//    Page<Objects[]> findInvitedUsersByUserId(Integer userId, Pageable pageable);
}
