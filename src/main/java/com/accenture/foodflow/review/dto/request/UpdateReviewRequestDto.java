package com.accenture.foodflow.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequestDto {

    private UUID reviewId;
    private String updatedContent;
    private Integer updatedRating;

}
