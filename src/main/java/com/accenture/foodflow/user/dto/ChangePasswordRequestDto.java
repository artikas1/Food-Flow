package com.accenture.foodflow.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
