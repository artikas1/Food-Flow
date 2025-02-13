package com.accenture.foodflow.review.repository;

import com.accenture.foodflow.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Review r SET r.isDisabled = true WHERE r.id = :reviewId")
    void disableReview(UUID reviewId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.target.id = :userId")
    double getAverageRating(UUID userId);

    List<Review> getReviewsByUserId(UUID userId);
}