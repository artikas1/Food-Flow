package com.accenture.foodflow.review.controller;

import com.accenture.foodflow.review.dto.request.SubmitReviewRequestDto;
import com.accenture.foodflow.review.dto.request.UpdateReviewRequestDto;
import com.accenture.foodflow.review.dto.response.ReviewResponseDto;
import com.accenture.foodflow.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "Review Controller", description = "APIs for managing food reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Create a new review", description = "Submits a new review for a food item")
    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDto> createReview(
            @Parameter(description = "Request body containing review details") @Valid @RequestBody SubmitReviewRequestDto submitReviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(submitReviewRequestDto));
    }

    @Operation(summary = "Update an existing review", description = "Updates an existing review with new details")
    @PostMapping("/update")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @Parameter(description = "Request body containing updated review details") @Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        return ResponseEntity.ok(reviewService.updateReview(updateReviewRequestDto));
    }

    @Operation(summary = "Delete a review", description = "Deletes a review by its ID")
    @DeleteMapping("/delete")
    public void deleteReview(
            @Parameter(description = "ID of the review to delete") @RequestParam("reviewId") UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @Operation(summary = "Get average rating", description = "Get the average rating of a user")
    @GetMapping("/average-rating")
    public double getAverageRating(
            @Parameter(description = "ID of the user to get the average rating for") @RequestParam("userId") UUID userId) {
        return reviewService.getAverageRating(userId);
    }
}