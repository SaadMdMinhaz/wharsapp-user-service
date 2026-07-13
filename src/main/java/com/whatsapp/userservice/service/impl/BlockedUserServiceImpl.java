package com.whatsapp.userservice.service.impl;

import com.whatsapp.userservice.dto.response.BlockedUserResponse;
import com.whatsapp.userservice.entity.BlockedUser;
import com.whatsapp.userservice.entity.User;
import com.whatsapp.userservice.exception.BlockedUserException;
import com.whatsapp.userservice.exception.UserNotFoundException;
import com.whatsapp.userservice.repository.BlockedUserRepository;
import com.whatsapp.userservice.repository.UserRepository;
import com.whatsapp.userservice.service.BlockedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BlockedUserServiceImpl implements BlockedUserService {

    private static final Logger log = LoggerFactory.getLogger(BlockedUserServiceImpl.class);

    private final BlockedUserRepository blockedUserRepository;
    private final UserRepository userRepository;

    public BlockedUserServiceImpl(BlockedUserRepository blockedUserRepository,
                                 UserRepository userRepository) {
        this.blockedUserRepository = blockedUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BlockedUserResponse blockUser(UUID blockerId, UUID blockedUserId) {
        log.info("Blocking user {} by blocker: {}", blockedUserId, blockerId);

        if (!userRepository.existsById(blockedUserId)) {
            log.error("User to block not found: {}", blockedUserId);
            throw new UserNotFoundException("User not found with id: " + blockedUserId);
        }

        if (blockedUserRepository.existsByBlockerIdAndBlockedId(blockerId, blockedUserId)) {
            log.warn("User {} is already blocked by: {}", blockedUserId, blockerId);
            throw new BlockedUserException("User is already blocked");
        }

        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setBlockerId(blockerId);
        blockedUser.setBlockedId(blockedUserId);
        blockedUser = blockedUserRepository.save(blockedUser);

        User blocked = userRepository.findById(blockedUserId).orElse(null);

        log.info("User blocked: {} by: {}", blockedUserId, blockerId);
        return toBlockedUserResponse(blockedUser, blocked);
    }

    @Override
    public void unblockUser(UUID blockerId, UUID blockedUserId) {
        log.info("Unblocking user {} by blocker: {}", blockedUserId, blockerId);
        BlockedUser blockedUser = blockedUserRepository
                .findByBlockerIdAndBlockedId(blockerId, blockedUserId)
                .orElseThrow(() -> {
                    log.error("Block relationship not found for blocker: {} and blocked: {}", blockerId, blockedUserId);
                    return new UserNotFoundException("Block relationship not found");
                });
        blockedUserRepository.delete(blockedUser);
        log.info("User unblocked: {} by: {}", blockedUserId, blockerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockedUserResponse> getBlockedUsers(UUID blockerId) {
        log.debug("Fetching blocked users for blocker: {}", blockerId);
        List<BlockedUser> blockedUsers = blockedUserRepository.findByBlockerId(blockerId);
        return blockedUsers.stream()
                .map(bu -> {
                    User blocked = userRepository.findById(bu.getBlockedId()).orElse(null);
                    return toBlockedUserResponse(bu, blocked);
                })
                .toList();
    }

    private BlockedUserResponse toBlockedUserResponse(BlockedUser blockedUser, User blocked) {
        String username = blocked != null ? blocked.getUsername() : null;
        String displayName = blocked != null ? blocked.getDisplayName() : null;
        String profilePictureUrl = blocked != null ? blocked.getProfilePictureUrl() : null;

        return new BlockedUserResponse(
                blockedUser.getId(),
                blockedUser.getBlockedId(),
                username,
                displayName,
                profilePictureUrl,
                blockedUser.getCreatedAt()
        );
    }
}
