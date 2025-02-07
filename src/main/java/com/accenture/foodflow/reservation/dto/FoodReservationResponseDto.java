package com.accenture.foodflow.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodReservationResponseDto {

    private UUID id;
    private UUID foodId;
    private UUID userId;
    private LocalDate reservationDate;
    private LocalDate expirationDate;
    private boolean isCancelled;

}
