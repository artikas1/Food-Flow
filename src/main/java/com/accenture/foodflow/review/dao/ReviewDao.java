package com.accenture.foodflow.review.dao;

import com.accenture.foodflow.review.entity.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewDao {

    Review saveReview(Review review);
    Review updateReview(Review review);
    Review findById(UUID reviewId);
    void deleteReview(UUID reviewId);
    double getAverageRating(UUID userId);
    List<Review> getReviewsByUserId(UUID userId);
}
