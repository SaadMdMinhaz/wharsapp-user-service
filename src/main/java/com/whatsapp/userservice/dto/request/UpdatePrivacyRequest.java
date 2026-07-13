package com.whatsapp.userservice.dto.request;

import com.whatsapp.userservice.enums.Visibility;
import jakarta.validation.constraints.NotNull;

public record UpdatePrivacyRequest(
        @NotNull(message = "Last seen visibility is required")
        Visibility lastSeenVisibility,

        @NotNull(message = "Profile photo visibility is required")
        Visibility profilePhotoVisibility,

        @NotNull(message = "About visibility is required")
        Visibility aboutVisibility,

        boolean readReceiptEnabled
) {
}
