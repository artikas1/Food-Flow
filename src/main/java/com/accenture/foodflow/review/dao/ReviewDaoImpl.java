package com.accenture.foodflow.review.dao;

import com.accenture.foodflow.common.exception.exceptions.EntityNotFoundException;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.review.integrity.ReviewDataIntegrity;
import com.accenture.foodflow.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewDaoImpl implements ReviewDao {

    public static final String REVIEW_NOT_FOUND = "Review not found";

    private final ReviewRepository reviewRepository;
    private final ReviewDataIntegrity reviewDataIntegrity;

    @Override
    public Review saveReview(Review review) {
        reviewDataIntegrity.validateReview(review);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        reviewDataIntegrity.validateReview(review);
        return reviewRepository.save(review);
    }

    @Override
    public Review findById(UUID reviewId) {
        reviewDataIntegrity.validateReviewExists(reviewId);
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(REVIEW_NOT_FOUND));
    }

    @Override
    public void deleteReview(UUID reviewId) {
        reviewDataIntegrity.validateReviewExists(reviewId);
        reviewRepository.disableReview(reviewId);
    }

    @Override
    public double getAverageRating(UUID userId) {
        return reviewRepository.getAverageRating(userId);
    }

    @Override
    public List<Review> getUserReviewsByTargetId(UUID userId) {
        return reviewRepository.findAllByTargetId(userId);
    }

}