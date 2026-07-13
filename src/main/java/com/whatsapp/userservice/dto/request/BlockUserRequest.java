package com.whatsapp.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BlockUserRequest(
        @NotNull(message = "Blocked user ID is required")
        UUID blockedUserId
) {
}
