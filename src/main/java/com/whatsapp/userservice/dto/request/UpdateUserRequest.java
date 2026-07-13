package com.whatsapp.userservice.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Size(max = 100, message = "Display name must not exceed 100 characters")
        String displayName,

        @Size(max = 500, message = "About must not exceed 500 characters")
        String about,

        @Size(max = 500, message = "Profile picture URL must not exceed 500 characters")
        String profilePictureUrl
) {
}
