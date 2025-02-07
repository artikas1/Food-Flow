package com.accenture.foodflow.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitReviewRequestDto {

    private String content;

    @NotNull(message = "RATING_IS_REQUIRED_FROM_1_TO_5")
    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    @NotNull(message = "Target is required")
    private UUID target;

}
