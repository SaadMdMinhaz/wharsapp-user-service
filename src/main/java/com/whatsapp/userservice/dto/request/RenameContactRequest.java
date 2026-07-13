package com.whatsapp.userservice.dto.request;

import jakarta.validation.constraints.Size;

public record RenameContactRequest(
        @Size(max = 100, message = "Nickname must not exceed 100 characters")
        String nickname
) {
}
