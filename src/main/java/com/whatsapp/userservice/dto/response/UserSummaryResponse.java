package com.whatsapp.userservice.dto.response;

import java.util.UUID;

public record UserSummaryResponse(
        UUID id,
        String username,
        String displayName,
        String profilePictureUrl
) {
}
