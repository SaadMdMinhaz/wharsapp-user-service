package com.whatsapp.userservice.service;

import com.whatsapp.userservice.dto.request.CreateUserRequest;
import com.whatsapp.userservice.dto.request.UpdateUserRequest;
import com.whatsapp.userservice.dto.response.SearchUserResponse;
import com.whatsapp.userservice.dto.response.UserResponse;
import com.whatsapp.userservice.dto.response.UserSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Creates a new user account.
     *
     * @param request the user creation request containing phone number, username, and profile info
     * @return the created user response
     * @throws com.whatsapp.userservice.exception.DuplicatePhoneException if phone number already exists
     * @throws com.whatsapp.userservice.exception.DuplicateUsernameException if username already exists
     */
    UserResponse createUser(CreateUserRequest request);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user UUID
     * @return the user response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    UserResponse getUserById(UUID id);

    /**
     * Returns a summary of the user suitable for contact listings.
     *
     * @param id the user UUID
     * @return the user summary response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    UserSummaryResponse getUserSummary(UUID id);

    /**
     * Updates an existing user's profile information.
     *
     * @param id      the user UUID
     * @param request the update request containing fields to modify
     * @return the updated user response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     * @throws com.whatsapp.userservice.exception.DuplicateUsernameException if new username already exists
     */
    UserResponse updateUser(UUID id, UpdateUserRequest request);

    /**
     * Soft-deletes a user by setting isActive to false.
     *
     * @param id the user UUID
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    void deactivateUser(UUID id);

    /**
     * Reactivates a previously deactivated user account.
     *
     * @param id the user UUID
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     */
    void reactivateUser(UUID id);

    /**
     * Searches for users by display name or username.
     *
     * @param query the search query string
     * @return list of matching user responses
     */
    List<SearchUserResponse> searchUsers(String query);
}
