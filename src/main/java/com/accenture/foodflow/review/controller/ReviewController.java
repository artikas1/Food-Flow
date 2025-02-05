package com.accenture.foodflow.review.controller;

import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.dto.response.ReviewResponseDto;
import com.accenture.foodflow.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody SubmitReviewRequestDto submitReviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(submitReviewRequestDto));
    }

    @PostMapping("/update")
    public ResponseEntity<ReviewResponseDto> updateReview(@Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        return ResponseEntity.ok(reviewService.updateReview(updateReviewRequestDto));
    }

    @DeleteMapping("/delete")
    public void deleteReview(@RequestParam("reviewId") UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }

}
