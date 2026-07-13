package com.whatsapp.userservice.repository;

import com.whatsapp.userservice.entity.UserPrivacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacy, UUID> {

    Optional<UserPrivacy> findByUserId(UUID userId);
}
