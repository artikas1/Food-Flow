package com.accenture.foodflow.review.mapper;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.common.exception.exceptions.EntityNotFoundException;
import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.response.ReviewResponseDto;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.user.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewMapper {

    public static final String USER_NOT_FOUND = "User not found";

    private final AuthenticationService authenticationService;
    private final UserDao userDao;

    public Review toEntity(SubmitReviewRequestDto submitReviewRequestDto) {
        return Review.builder()
                .content(submitReviewRequestDto.getContent())
                .rating(submitReviewRequestDto.getRating())
                .author(authenticationService.getAuthenticatedUser())
                .target(userDao.findById(submitReviewRequestDto.getTarget()).
                        orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)))
                .isDisabled(false)
                .build();
    }

    public ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

}
