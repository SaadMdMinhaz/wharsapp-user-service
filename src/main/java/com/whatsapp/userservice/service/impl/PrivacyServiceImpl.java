package com.whatsapp.userservice.service.impl;

import com.whatsapp.userservice.dto.request.UpdatePrivacyRequest;
import com.whatsapp.userservice.dto.response.PrivacyResponse;
import com.whatsapp.userservice.entity.UserPrivacy;
import com.whatsapp.userservice.exception.UserNotFoundException;
import com.whatsapp.userservice.mapper.PrivacyMapper;
import com.whatsapp.userservice.repository.UserPrivacyRepository;
import com.whatsapp.userservice.repository.UserRepository;
import com.whatsapp.userservice.service.PrivacyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PrivacyServiceImpl implements PrivacyService {

    private static final Logger log = LoggerFactory.getLogger(PrivacyServiceImpl.class);

    private final UserPrivacyRepository privacyRepository;
    private final UserRepository userRepository;
    private final PrivacyMapper privacyMapper;

    public PrivacyServiceImpl(UserPrivacyRepository privacyRepository,
                             UserRepository userRepository,
                             PrivacyMapper privacyMapper) {
        this.privacyRepository = privacyRepository;
        this.userRepository = userRepository;
        this.privacyMapper = privacyMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PrivacyResponse getPrivacy(UUID userId) {
        log.debug("Fetching privacy settings for user: {}", userId);
        ensureUserExists(userId);
        UserPrivacy privacy = findPrivacyOrThrow(userId);
        return privacyMapper.toResponse(privacy);
    }

    @Override
    public PrivacyResponse updatePrivacy(UUID userId, UpdatePrivacyRequest request) {
        log.info("Updating privacy settings for user: {}", userId);
        ensureUserExists(userId);
        UserPrivacy privacy = findPrivacyOrThrow(userId);
        privacyMapper.updateEntity(request, privacy);
        privacy = privacyRepository.save(privacy);
        log.info("Privacy settings updated for user: {}", userId);
        return privacyMapper.toResponse(privacy);
    }

    private UserPrivacy findPrivacyOrThrow(UUID userId) {
        return privacyRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Privacy settings not found for user: {}", userId);
                    return new UserNotFoundException("Privacy settings not found for user: " + userId);
                });
    }

    private void ensureUserExists(UUID userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User not found with id: {}", userId);
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }
}
