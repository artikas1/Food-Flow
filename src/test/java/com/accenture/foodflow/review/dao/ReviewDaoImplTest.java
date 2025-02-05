package com.accenture.foodflow.review.dao;

import com.accenture.foodflow.common.exception.exceptions.EntityNotFoundException;
import com.accenture.foodflow.review.entity.Review;
import com.accenture.foodflow.review.integrity.ReviewDataIntegrity;
import com.accenture.foodflow.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewDaoImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewDataIntegrity reviewDataIntegrity;

    @InjectMocks
    private ReviewDaoImpl reviewDao;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setId(UUID.randomUUID());
        review.setContent("This is a great review!");
        review.setRating(5);
    }

    @Test
    void saveReview_ShouldReturnSavedReview() {
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewDao.saveReview(review);

        assertNotNull(result);
        assertEquals(review, result);
        verify(reviewDataIntegrity, times(1)).validateReview(review);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void updateReview_ShouldReturnUpdatedReview() {
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewDao.updateReview(review);

        assertNotNull(result);
        assertEquals(review, result);
        verify(reviewDataIntegrity, times(1)).validateReview(review);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void findById_ShouldReturnReview() {
        UUID reviewId = review.getId();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Review result = reviewDao.findById(reviewId);

        assertNotNull(result);
        assertEquals(review, result);
        verify(reviewDataIntegrity, times(1)).validateReviewExists(reviewId);
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException() {
        UUID reviewId = UUID.randomUUID();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewDao.findById(reviewId));
        verify(reviewDataIntegrity, times(1)).validateReviewExists(reviewId);
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void deleteReview_ShouldDisableReview() {
        UUID reviewId = review.getId();

        doNothing().when(reviewRepository).disableReview(reviewId);

        reviewDao.deleteReview(reviewId);

        verify(reviewDataIntegrity, times(1)).validateReviewExists(reviewId);
        verify(reviewRepository, times(1)).disableReview(reviewId);
    }
}