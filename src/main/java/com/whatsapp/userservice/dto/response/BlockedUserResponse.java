package com.whatsapp.userservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedUserResponse(
        UUID id,
        UUID blockedUserId,
        String username,
        String displayName,
        String profilePictureUrl,
        LocalDateTime createdAt
) {
}
