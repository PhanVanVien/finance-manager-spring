package com.minimalcode.financemanager.dto.auth.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Pattern(
            regexp = "^[a-zA-Z]{2,12}$",
            message = "Last Name must contain only letters and be 2 to 12 characters long"
    )
    private String lastName;

    @Pattern(
            regexp = "^[a-zA-Z]{2,12}$",
            message = "First Name must contain only letters and be 2 to 12 characters long"
    )
    private String firstName;

    @Pattern(
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Email should be in the format example@example.com"
    )
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and be 8 to 16 characters long"
    )
    private String password;
}