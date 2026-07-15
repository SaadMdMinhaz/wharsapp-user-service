package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.CreateUserRequest;
import com.whatsapp.userservice.dto.request.UpdateUserRequest;
import com.whatsapp.userservice.dto.response.SearchUserResponse;
import com.whatsapp.userservice.dto.response.UserResponse;
import com.whatsapp.userservice.dto.response.UserSummaryResponse;
import com.whatsapp.userservice.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-15T14:24:46+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setId( request.id() );
        user.setPhoneNumber( request.phoneNumber() );
        user.setUsername( request.username() );
        user.setDisplayName( request.displayName() );
        user.setAbout( request.about() );
        user.setProfilePictureUrl( request.profilePictureUrl() );

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String phoneNumber = null;
        String username = null;
        String displayName = null;
        String about = null;
        String profilePictureUrl = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = user.getId();
        phoneNumber = user.getPhoneNumber();
        username = user.getUsername();
        displayName = user.getDisplayName();
        about = user.getAbout();
        profilePictureUrl = user.getProfilePictureUrl();
        createdAt = user.getCreatedAt();
        updatedAt = user.getUpdatedAt();

        boolean isActive = false;

        UserResponse userResponse = new UserResponse( id, phoneNumber, username, displayName, about, profilePictureUrl, isActive, createdAt, updatedAt );

        return userResponse;
    }

    @Override
    public UserSummaryResponse toSummaryResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String displayName = null;
        String profilePictureUrl = null;

        id = user.getId();
        username = user.getUsername();
        displayName = user.getDisplayName();
        profilePictureUrl = user.getProfilePictureUrl();

        UserSummaryResponse userSummaryResponse = new UserSummaryResponse( id, username, displayName, profilePictureUrl );

        return userSummaryResponse;
    }

    @Override
    public SearchUserResponse toSearchResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String displayName = null;
        String phoneNumber = null;
        String profilePictureUrl = null;
        String about = null;

        id = user.getId();
        username = user.getUsername();
        displayName = user.getDisplayName();
        phoneNumber = user.getPhoneNumber();
        profilePictureUrl = user.getProfilePictureUrl();
        about = user.getAbout();

        SearchUserResponse searchUserResponse = new SearchUserResponse( id, username, displayName, phoneNumber, profilePictureUrl, about );

        return searchUserResponse;
    }

    @Override
    public void updateEntity(UpdateUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        if ( request.username() != null ) {
            user.setUsername( request.username() );
        }
        if ( request.displayName() != null ) {
            user.setDisplayName( request.displayName() );
        }
        if ( request.about() != null ) {
            user.setAbout( request.about() );
        }
        if ( request.profilePictureUrl() != null ) {
            user.setProfilePictureUrl( request.profilePictureUrl() );
        }
    }
}
