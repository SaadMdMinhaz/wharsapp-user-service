package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.AddContactRequest;
import com.whatsapp.userservice.dto.response.ContactResponse;
import com.whatsapp.userservice.entity.Contact;
import com.whatsapp.userservice.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-14T12:48:48+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact toEntity(AddContactRequest request) {
        if ( request == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setContactUserId( request.contactUserId() );
        contact.setNickname( request.nickname() );

        return contact;
    }

    @Override
    public ContactResponse toResponse(Contact contact, User user) {
        if ( contact == null && user == null ) {
            return null;
        }

        UUID id = null;
        UUID contactUserId = null;
        String nickname = null;
        LocalDateTime createdAt = null;
        if ( contact != null ) {
            id = contact.getId();
            contactUserId = contact.getContactUserId();
            nickname = contact.getNickname();
            createdAt = contact.getCreatedAt();
        }
        String displayName = null;
        String username = null;
        String profilePictureUrl = null;
        if ( user != null ) {
            displayName = user.getDisplayName();
            username = user.getUsername();
            profilePictureUrl = user.getProfilePictureUrl();
        }

        ContactResponse contactResponse = new ContactResponse( id, contactUserId, nickname, displayName, username, profilePictureUrl, createdAt );

        return contactResponse;
    }
}
