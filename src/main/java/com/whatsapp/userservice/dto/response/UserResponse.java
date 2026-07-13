package com.whatsapp.userservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String phoneNumber,
        String username,
        String displayName,
        String about,
        String profilePictureUrl,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
