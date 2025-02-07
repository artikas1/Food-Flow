package com.accenture.foodflow.review.integrity;

import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.user.entity.User;
import java.util.UUID;

public interface ReviewDataIntegrity {

    void validateReview(Review review);
    void validateSubmitDto(SubmitReviewRequestDto dto);
    void validateUpdateDto(UpdateReviewRequestDto dto);
    void validateUserNotReviewingSelf(User user, SubmitReviewRequestDto dto);
    void validateReviewExists(UUID reviewId);
    void validateUserNotReviewingOther(UpdateReviewRequestDto dto);
    void validateUserNotDeletingOther(UUID reviewId);

}