package com.whatsapp.userservice.service.impl;

import com.whatsapp.userservice.dto.request.CreateUserRequest;
import com.whatsapp.userservice.dto.request.UpdateUserRequest;
import com.whatsapp.userservice.dto.response.SearchUserResponse;
import com.whatsapp.userservice.dto.response.UserResponse;
import com.whatsapp.userservice.dto.response.UserSummaryResponse;
import com.whatsapp.userservice.entity.User;
import com.whatsapp.userservice.entity.UserPrivacy;
import com.whatsapp.userservice.enums.Visibility;
import com.whatsapp.userservice.exception.DuplicatePhoneException;
import com.whatsapp.userservice.exception.DuplicateUsernameException;
import com.whatsapp.userservice.exception.UserNotFoundException;
import com.whatsapp.userservice.mapper.UserMapper;
import com.whatsapp.userservice.repository.UserPrivacyRepository;
import com.whatsapp.userservice.repository.UserRepository;
import com.whatsapp.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserPrivacyRepository userPrivacyRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                          UserPrivacyRepository userPrivacyRepository,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userPrivacyRepository = userPrivacyRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with phone number: {}", request.phoneNumber());

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            log.warn("Duplicate phone number: {}", request.phoneNumber());
            throw new DuplicatePhoneException("Phone number already registered: " + request.phoneNumber());
        }

        if (userRepository.existsByUsername(request.username())) {
            log.warn("Duplicate username: {}", request.username());
            throw new DuplicateUsernameException("Username already taken: " + request.username());
        }

        User user = userMapper.toEntity(request);
        user = userRepository.save(user);

        createDefaultPrivacy(user.getId());

        log.info("User created with id: {}", user.getId());
        return userMapper.toResponse(user);
    }

    private void createDefaultPrivacy(UUID userId) {
        UserPrivacy privacy = new UserPrivacy();
        privacy.setUserId(userId);
        privacy.setLastSeenVisibility(Visibility.EVERYONE);
        privacy.setProfilePhotoVisibility(Visibility.EVERYONE);
        privacy.setAboutVisibility(Visibility.EVERYONE);
        privacy.setReadReceiptEnabled(true);
        userPrivacyRepository.save(privacy);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        log.debug("Fetching user by id: {}", id);
        User user = findUserOrThrow(id);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummaryResponse getUserSummary(UUID id) {
        log.debug("Fetching user summary by id: {}", id);
        User user = findUserOrThrow(id);
        return userMapper.toSummaryResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);
        User user = findUserOrThrow(id);

        if (request.username() != null && !request.username().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.username())) {
                log.warn("Duplicate username during update: {}", request.username());
                throw new DuplicateUsernameException("Username already taken: " + request.username());
            }
        }

        userMapper.updateEntity(request, user);
        user = userRepository.save(user);

        log.info("User updated with id: {}", id);
        return userMapper.toResponse(user);
    }

    @Override
    public void deactivateUser(UUID id) {
        log.info("Deactivating user with id: {}", id);
        User user = findUserOrThrow(id);
        user.setActive(false);
        userRepository.save(user);
        log.info("User deactivated with id: {}", id);
    }

    @Override
    public void reactivateUser(UUID id) {
        log.info("Reactivating user with id: {}", id);
        User user = findUserOrThrow(id);
        user.setActive(true);
        userRepository.save(user);
        log.info("User reactivated with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchUserResponse> searchUsers(String query) {
        log.debug("Searching users with query: {}", query);
        List<User> users = userRepository
                .findByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(query, query);
        return users.stream()
                .map(userMapper::toSearchResponse)
                .toList();
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });
    }
}
