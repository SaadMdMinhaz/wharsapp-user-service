package com.whatsapp.userservice.dto.response;

import java.util.UUID;

public record SearchUserResponse(
        UUID id,
        String username,
        String displayName,
        String phoneNumber,
        String profilePictureUrl,
        String about
) {
}
