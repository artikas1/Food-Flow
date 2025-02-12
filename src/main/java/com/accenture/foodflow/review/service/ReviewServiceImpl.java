package com.accenture.foodflow.review.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.review.dao.ReviewDao;
import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.dto.response.ReviewResponseDto;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.review.integrity.ReviewDataIntegrity;
import com.accenture.foodflow.review.mapper.ReviewMapper;
import com.accenture.foodflow.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDao reviewDao;
    private final ReviewMapper reviewMapper;
    private final ReviewDataIntegrity reviewDataIntegrity;
    private final AuthenticationService authenticationService;

    @Override
    public ReviewResponseDto createReview(SubmitReviewRequestDto dto) {
        reviewDataIntegrity.validateSubmitDto(dto);
        User user = authenticationService.getAuthenticatedUser();
        reviewDataIntegrity.validateUserNotReviewingSelf(user, dto);

        Review review = reviewMapper.toEntity(dto);
        review.setAuthor(user);
        return reviewMapper.toDto(reviewDao.saveReview(review));
    }

    @Override
    public ReviewResponseDto updateReview(UpdateReviewRequestDto dto) {
        reviewDataIntegrity.validateUpdateDto(dto);
        reviewDataIntegrity.validateUserNotReviewingOther(dto);

        Review review = reviewDao.findById(dto.getReviewId());
        review.setContent(dto.getUpdatedContent());
        review.setRating(dto.getUpdatedRating());

        return reviewMapper.toDto(reviewDao.updateReview(review));
    }

    @Override
    public void deleteReview(UUID reviewId) {
        reviewDataIntegrity.validateReviewExists(reviewId);
        reviewDataIntegrity.validateUserNotDeletingOther(reviewId);
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public double getAverageRating(UUID userId) {
        return reviewDao.getAverageRating(userId);
    }

}
