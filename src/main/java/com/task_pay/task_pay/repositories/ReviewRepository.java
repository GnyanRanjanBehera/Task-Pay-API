package com.task_pay.task_pay.repositories;
import com.task_pay.task_pay.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review,Integer> {
}
