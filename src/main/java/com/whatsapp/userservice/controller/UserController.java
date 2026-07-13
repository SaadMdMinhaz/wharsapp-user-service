package com.whatsapp.userservice.controller;

import com.whatsapp.userservice.constant.ApiConstants;
import com.whatsapp.userservice.dto.request.AddContactRequest;
import com.whatsapp.userservice.dto.request.BlockUserRequest;
import com.whatsapp.userservice.dto.request.CreateUserRequest;
import com.whatsapp.userservice.dto.request.RenameContactRequest;
import com.whatsapp.userservice.dto.request.UpdatePrivacyRequest;
import com.whatsapp.userservice.dto.request.UpdateUserRequest;
import com.whatsapp.userservice.dto.response.BlockedUserResponse;
import com.whatsapp.userservice.dto.response.ContactResponse;
import com.whatsapp.userservice.dto.response.PrivacyResponse;
import com.whatsapp.userservice.dto.response.SearchUserResponse;
import com.whatsapp.userservice.dto.response.UserResponse;
import com.whatsapp.userservice.service.BlockedUserService;
import com.whatsapp.userservice.service.ContactService;
import com.whatsapp.userservice.service.PrivacyService;
import com.whatsapp.userservice.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstants.API_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PrivacyService privacyService;
    private final ContactService contactService;
    private final BlockedUserService blockedUserService;

    public UserController(UserService userService,
                         PrivacyService privacyService,
                         ContactService contactService,
                         BlockedUserService blockedUserService) {
        this.userService = userService;
        this.privacyService = privacyService;
        this.contactService = contactService;
        this.blockedUserService = blockedUserService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("POST /users - create user request");
        UserResponse response = userService.createUser(request);
        return ResponseEntity
                .created(URI.create(ApiConstants.API_BASE_PATH + "/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        log.info("GET /users/{}", id);
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @Valid @RequestBody UpdateUserRequest request) {
        log.info("PUT /users/{}", id);
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable UUID id) {
        log.info("DELETE /users/{}", id);
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> reactivateUser(@PathVariable UUID id) {
        log.info("PATCH /users/{}/activate", id);
        userService.reactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchUserResponse>> searchUsers(@RequestParam String q) {
        log.info("GET /users/search?q={}", q);
        List<SearchUserResponse> responses = userService.searchUsers(q);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactResponse>> getContacts(Authentication authentication) {
        UUID userId = extractUserId(authentication);
        log.info("GET /users/contacts for user: {}", userId);
        List<ContactResponse> responses = contactService.getContacts(userId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactResponse> addContact(Authentication authentication,
                                                      @Valid @RequestBody AddContactRequest request) {
        UUID userId = extractUserId(authentication);
        log.info("POST /users/contacts for user: {}", userId);
        ContactResponse response = contactService.addContact(userId, request);
        return ResponseEntity
                .created(URI.create(ApiConstants.API_CONTACTS_PATH + "/" + response.id()))
                .body(response);
    }

    @PutMapping("/contacts/{contactId}")
    public ResponseEntity<ContactResponse> renameContact(Authentication authentication,
                                                         @PathVariable UUID contactId,
                                                         @Valid @RequestBody RenameContactRequest request) {
        UUID userId = extractUserId(authentication);
        log.info("PUT /users/contacts/{} for user: {}", contactId, userId);
        ContactResponse response = contactService.renameContact(userId, contactId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<Void> deleteContact(Authentication authentication,
                                              @PathVariable UUID contactId) {
        UUID userId = extractUserId(authentication);
        log.info("DELETE /users/contacts/{} for user: {}", contactId, userId);
        contactService.deleteContact(userId, contactId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/privacy")
    public ResponseEntity<PrivacyResponse> getPrivacy(Authentication authentication) {
        UUID userId = extractUserId(authentication);
        log.info("GET /users/privacy for user: {}", userId);
        PrivacyResponse response = privacyService.getPrivacy(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/privacy")
    public ResponseEntity<PrivacyResponse> updatePrivacy(Authentication authentication,
                                                         @Valid @RequestBody UpdatePrivacyRequest request) {
        UUID userId = extractUserId(authentication);
        log.info("PUT /users/privacy for user: {}", userId);
        PrivacyResponse response = privacyService.updatePrivacy(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/block")
    public ResponseEntity<BlockedUserResponse> blockUser(Authentication authentication,
                                                         @Valid @RequestBody BlockUserRequest request) {
        UUID userId = extractUserId(authentication);
        log.info("POST /users/block by user: {}", userId);
        BlockedUserResponse response = blockedUserService.blockUser(userId, request.blockedUserId());
        return ResponseEntity
                .created(URI.create("/api/v1/users/block"))
                .body(response);
    }

    @DeleteMapping("/block/{blockedUserId}")
    public ResponseEntity<Void> unblockUser(Authentication authentication,
                                            @PathVariable UUID blockedUserId) {
        UUID userId = extractUserId(authentication);
        log.info("DELETE /users/block/{} by user: {}", blockedUserId, userId);
        blockedUserService.unblockUser(userId, blockedUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/blocked")
    public ResponseEntity<List<BlockedUserResponse>> getBlockedUsers(Authentication authentication) {
        UUID userId = extractUserId(authentication);
        log.info("GET /users/blocked for user: {}", userId);
        List<BlockedUserResponse> responses = blockedUserService.getBlockedUsers(userId);
        return ResponseEntity.ok(responses);
    }

    private UUID extractUserId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
