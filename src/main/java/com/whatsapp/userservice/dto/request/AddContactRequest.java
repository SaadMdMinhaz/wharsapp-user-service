package com.whatsapp.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record AddContactRequest(
        @NotBlank(message = "Contact user ID is required")
        UUID contactUserId,

        @Size(max = 100, message = "Nickname must not exceed 100 characters")
        String nickname
) {
}
