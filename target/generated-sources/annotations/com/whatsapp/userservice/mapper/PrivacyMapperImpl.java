package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.UpdatePrivacyRequest;
import com.whatsapp.userservice.dto.response.PrivacyResponse;
import com.whatsapp.userservice.entity.UserPrivacy;
import com.whatsapp.userservice.enums.Visibility;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-14T12:48:49+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class PrivacyMapperImpl implements PrivacyMapper {

    @Override
    public UserPrivacy toEntity(UpdatePrivacyRequest request) {
        if ( request == null ) {
            return null;
        }

        UserPrivacy userPrivacy = new UserPrivacy();

        userPrivacy.setLastSeenVisibility( request.lastSeenVisibility() );
        userPrivacy.setProfilePhotoVisibility( request.profilePhotoVisibility() );
        userPrivacy.setAboutVisibility( request.aboutVisibility() );
        userPrivacy.setReadReceiptEnabled( request.readReceiptEnabled() );

        return userPrivacy;
    }

    @Override
    public PrivacyResponse toResponse(UserPrivacy privacy) {
        if ( privacy == null ) {
            return null;
        }

        Visibility lastSeenVisibility = null;
        Visibility profilePhotoVisibility = null;
        Visibility aboutVisibility = null;
        boolean readReceiptEnabled = false;

        lastSeenVisibility = privacy.getLastSeenVisibility();
        profilePhotoVisibility = privacy.getProfilePhotoVisibility();
        aboutVisibility = privacy.getAboutVisibility();
        readReceiptEnabled = privacy.isReadReceiptEnabled();

        PrivacyResponse privacyResponse = new PrivacyResponse( lastSeenVisibility, profilePhotoVisibility, aboutVisibility, readReceiptEnabled );

        return privacyResponse;
    }

    @Override
    public void updateEntity(UpdatePrivacyRequest request, UserPrivacy privacy) {
        if ( request == null ) {
            return;
        }

        if ( request.lastSeenVisibility() != null ) {
            privacy.setLastSeenVisibility( request.lastSeenVisibility() );
        }
        if ( request.profilePhotoVisibility() != null ) {
            privacy.setProfilePhotoVisibility( request.profilePhotoVisibility() );
        }
        if ( request.aboutVisibility() != null ) {
            privacy.setAboutVisibility( request.aboutVisibility() );
        }
        privacy.setReadReceiptEnabled( request.readReceiptEnabled() );
    }
}
