package com.accenture.foodflow.review.service;

import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.dto.response.GetAllReviewResponseDto;
import com.accenture.foodflow.review.dto.response.ReviewResponseDto;
import java.util.List;
import java.util.UUID;

public interface ReviewService {

    ReviewResponseDto createReview(SubmitReviewRequestDto submitReviewRequestDto);
    ReviewResponseDto updateReview(UpdateReviewRequestDto updateReviewRequestDto);
    void deleteReview(UUID reviewId);
    double getAverageRating(UUID userId);
    List<GetAllReviewResponseDto> getUserReviews(UUID userId);

}
