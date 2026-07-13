package com.whatsapp.userservice.service;

import com.whatsapp.userservice.dto.response.BlockedUserResponse;

import java.util.List;
import java.util.UUID;

public interface BlockedUserService {

    /**
     * Blocks a user for the specified blocker.
     *
     * @param blockerId    the blocker user UUID
     * @param blockedUserId the user to block
     * @return the blocked user response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if user not found
     * @throws com.whatsapp.userservice.exception.BlockedUserException if user is already blocked
     */
    BlockedUserResponse blockUser(UUID blockerId, UUID blockedUserId);

    /**
     * Unblocks a previously blocked user.
     *
     * @param blockerId     the blocker user UUID
     * @param blockedUserId the user to unblock
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if block relationship not found
     */
    void unblockUser(UUID blockerId, UUID blockedUserId);

    /**
     * Retrieves all blocked users for the specified user.
     *
     * @param blockerId the blocker user UUID
     * @return list of blocked user responses
     */
    List<BlockedUserResponse> getBlockedUsers(UUID blockerId);
}
