package com.whatsapp.userservice.dto.response;

import com.whatsapp.userservice.enums.Visibility;

public record PrivacyResponse(
        Visibility lastSeenVisibility,
        Visibility profilePhotoVisibility,
        Visibility aboutVisibility,
        boolean readReceiptEnabled
) {
}
