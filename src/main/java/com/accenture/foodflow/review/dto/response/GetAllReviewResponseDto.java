package com.accenture.foodflow.review.dto.response;

import com.accenture.foodflow.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllReviewResponseDto {

    private ReviewResponseDto review;
    private UserResponseDto user;

}
