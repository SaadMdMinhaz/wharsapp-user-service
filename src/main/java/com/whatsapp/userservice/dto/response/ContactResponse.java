package com.whatsapp.userservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContactResponse(
        UUID id,
        UUID contactUserId,
        String nickname,
        String displayName,
        String username,
        String profilePictureUrl,
        LocalDateTime createdAt
) {
}
