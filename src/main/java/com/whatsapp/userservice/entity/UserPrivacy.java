package com.whatsapp.userservice.entity;

import com.whatsapp.userservice.enums.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_privacy")
public class UserPrivacy {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_seen_visibility", nullable = false, length = 20)
    private Visibility lastSeenVisibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_photo_visibility", nullable = false, length = 20)
    private Visibility profilePhotoVisibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "about_visibility", nullable = false, length = 20)
    private Visibility aboutVisibility;

    @Column(name = "read_receipt_enabled", nullable = false)
    private boolean readReceiptEnabled;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Visibility getLastSeenVisibility() {
        return lastSeenVisibility;
    }

    public void setLastSeenVisibility(Visibility lastSeenVisibility) {
        this.lastSeenVisibility = lastSeenVisibility;
    }

    public Visibility getProfilePhotoVisibility() {
        return profilePhotoVisibility;
    }

    public void setProfilePhotoVisibility(Visibility profilePhotoVisibility) {
        this.profilePhotoVisibility = profilePhotoVisibility;
    }

    public Visibility getAboutVisibility() {
        return aboutVisibility;
    }

    public void setAboutVisibility(Visibility aboutVisibility) {
        this.aboutVisibility = aboutVisibility;
    }

    public boolean isReadReceiptEnabled() {
        return readReceiptEnabled;
    }

    public void setReadReceiptEnabled(boolean readReceiptEnabled) {
        this.readReceiptEnabled = readReceiptEnabled;
    }
}
