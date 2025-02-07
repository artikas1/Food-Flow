package com.accenture.foodflow.user.dto;

import com.accenture.foodflow.user.type.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Gender gender;
    // should not have password for security reasons
}
