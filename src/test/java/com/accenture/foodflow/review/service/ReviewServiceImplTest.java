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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ReviewDataIntegrity reviewDataIntegrity;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private SubmitReviewRequestDto submitReviewRequestDto;
    private UpdateReviewRequestDto updateReviewRequestDto;
    private Review review;
    private ReviewResponseDto reviewResponseDto;
    private User user;

    @BeforeEach
    void setUp() {
        submitReviewRequestDto = new SubmitReviewRequestDto();
        submitReviewRequestDto.setTarget(UUID.randomUUID());
        submitReviewRequestDto.setContent("This is a great review!");
        submitReviewRequestDto.setRating(5);

        updateReviewRequestDto = new UpdateReviewRequestDto();
        updateReviewRequestDto.setReviewId(UUID.randomUUID());
        updateReviewRequestDto.setUpdatedContent("Updated review content");
        updateReviewRequestDto.setUpdatedRating(4);

        review = new Review();
        review.setId(UUID.randomUUID());
        review.setContent("This is a great review!");
        review.setRating(5);

        reviewResponseDto = new ReviewResponseDto();
        reviewResponseDto.setContent(review.getContent());
        reviewResponseDto.setRating(review.getRating());

        user = new User();
        user.setId(UUID.randomUUID());
    }

    @Test
    void createReview_ShouldReturnReviewResponseDto() {
        when(authenticationService.getAuthenticatedUser()).thenReturn(user);
        when(reviewMapper.toEntity(submitReviewRequestDto)).thenReturn(review);
        when(reviewDao.saveReview(review)).thenReturn(review);
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        ReviewResponseDto result = reviewService.createReview(submitReviewRequestDto);

        assertNotNull(result);
        assertEquals(reviewResponseDto, result);
        verify(reviewDataIntegrity, times(1)).validateSubmitDto(submitReviewRequestDto);
        verify(reviewDataIntegrity, times(1)).validateUserNotReviewingSelf(user, submitReviewRequestDto);
        verify(reviewDao, times(1)).saveReview(review);
    }

    @Test
    void updateReview_ShouldReturnUpdatedReviewResponseDto() {
        when(reviewDao.findById(updateReviewRequestDto.getReviewId())).thenReturn(review);
        when(reviewDao.updateReview(review)).thenReturn(review);
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        ReviewResponseDto result = reviewService.updateReview(updateReviewRequestDto);

        assertNotNull(result);
        assertEquals(reviewResponseDto, result);
        verify(reviewDataIntegrity, times(1)).validateUpdateDto(updateReviewRequestDto);
        verify(reviewDataIntegrity, times(1)).validateUserNotReviewingOther(updateReviewRequestDto);
        verify(reviewDao, times(1)).updateReview(review);
    }

    @Test
    void deleteReview_ShouldDeleteReview() {
        UUID reviewId = UUID.randomUUID();

        doNothing().when(reviewDao).deleteReview(reviewId);

        reviewService.deleteReview(reviewId);

        verify(reviewDataIntegrity, times(1)).validateReviewExists(reviewId);
        verify(reviewDataIntegrity, times(1)).validateUserNotDeletingOther(reviewId);
        verify(reviewDao, times(1)).deleteReview(reviewId);
    }
}