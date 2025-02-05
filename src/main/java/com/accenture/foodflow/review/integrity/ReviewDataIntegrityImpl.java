package com.accenture.foodflow.review.integrity;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.common.exception.exceptions.DtoValidationException;
import com.accenture.foodflow.common.exception.exceptions.InvalidContentException;
import com.accenture.foodflow.common.exception.exceptions.InvalidRatingException;
import com.accenture.foodflow.common.exception.exceptions.ReviewValidationException;
import com.accenture.foodflow.common.exception.exceptions.SelfReviewException;
import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.review.repository.ReviewRepository;
import com.accenture.foodflow.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewDataIntegrityImpl implements ReviewDataIntegrity {

    public static final String REVIEW_NOT_FOUND = "Review not found";
    public static final String REVIEW_CANNOT_BE_NULL = "Review cannot be null";
    public static final String SUBMIT_DTO_CANNOT_BE_NULL = "SubmitReviewRequestDto cannot be null";
    public static final String UPDATE_DTO_CANNOT_BE_NULL = "UpdateReviewRequestDto cannot be null";
    public static final String USER_CANNOT_REVIEW_SELF = "User cannot review themselves";
    public static final String INVALID_RATING = "Rating must be between 1 and 5";
    public static final String CONTENT_TOO_SHORT = "Review content must be at least 10 characters";
    public static final String CONTENT_TOO_LONG = "Review content cannot exceed 2000 characters";
    public static final String TARGET_USER_REQUIRED = "Target user ID is required";
    private static final int MIN_CONTENT_LENGTH = 10;
    private static final int MAX_CONTENT_LENGTH = 2000;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    public static final String USER_CANNOT_UPDATE_OTHER_USER_S_REVIEW = "User cannot update other user's review";
    public static final String USER_CANNOT_DELETE_OTHER_USER_S_REVIEW = "User cannot delete other user's review";

    private final ReviewRepository reviewRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void validateReview(Review review) {
        if (review == null) {
            throw new ReviewValidationException(REVIEW_CANNOT_BE_NULL);
        }
        validateContent(review.getContent());
        validateRating(review.getRating());
    }

    @Override
    public void validateSubmitDto(SubmitReviewRequestDto dto) {
        if (dto == null) {
            throw new DtoValidationException(SUBMIT_DTO_CANNOT_BE_NULL);
        }
        if (dto.getTarget() == null) {
            throw new DtoValidationException(TARGET_USER_REQUIRED);
        }
        validateContent(dto.getContent());
        validateRating(dto.getRating());
    }

    @Override
    public void validateUpdateDto(UpdateReviewRequestDto dto) {
        if (dto == null) {
            throw new DtoValidationException(UPDATE_DTO_CANNOT_BE_NULL);
        }
        validateContent(dto.getUpdatedContent());
        validateRating(dto.getUpdatedRating());
    }

    @Override
    public void validateUserNotReviewingSelf(User user, SubmitReviewRequestDto dto) {
        if (user.getId().equals(dto.getTarget())) {
            throw new SelfReviewException(USER_CANNOT_REVIEW_SELF);
        }
    }

    @Override
    public void validateReviewExists(UUID reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND);
        }
    }

    @Override
    public void validateUserNotReviewingOther(UpdateReviewRequestDto dto) {
        User currentUSer = authenticationService.getAuthenticatedUser();
        Optional<Review> review = reviewRepository.findById(dto.getReviewId());

        if (review.isPresent() && !review.get().getAuthor().getId().equals(currentUSer.getId())) {
            throw new ReviewValidationException(USER_CANNOT_UPDATE_OTHER_USER_S_REVIEW);
        }
    }

    @Override
    public void validateUserNotDeletingOther(UUID reviewId) {
        User currentUser = authenticationService.getAuthenticatedUser();
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent() && !review.get().getAuthor().getId().equals(currentUser.getId())) {
            throw new ReviewValidationException(USER_CANNOT_DELETE_OTHER_USER_S_REVIEW);
        }
    }

    private void validateContent(String content) {
        if (content == null || content.trim().length() < MIN_CONTENT_LENGTH) {
            throw new InvalidContentException(CONTENT_TOO_SHORT);
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidContentException(CONTENT_TOO_LONG);
        }
    }

    private void validateRating(int rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new InvalidRatingException(INVALID_RATING);
        }
    }

}