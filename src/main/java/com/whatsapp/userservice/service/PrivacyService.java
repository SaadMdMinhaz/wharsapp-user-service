package com.whatsapp.userservice.service;

import com.whatsapp.userservice.dto.request.UpdatePrivacyRequest;
import com.whatsapp.userservice.dto.response.PrivacyResponse;

import java.util.UUID;

public interface PrivacyService {

    /**
     * Retrieves privacy settings for a user.
     *
     * @param userId the user UUID
     * @return the privacy response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    PrivacyResponse getPrivacy(UUID userId);

    /**
     * Updates privacy settings for a user.
     *
     * @param userId  the user UUID
     * @param request the privacy update request
     * @return the updated privacy response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    PrivacyResponse updatePrivacy(UUID userId, UpdatePrivacyRequest request);
}
