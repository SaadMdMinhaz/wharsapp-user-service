package com.whatsapp.userservice.repository;

import com.whatsapp.userservice.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findByOwnerUserId(UUID ownerUserId);

    Optional<Contact> findByOwnerUserIdAndContactUserId(UUID ownerUserId, UUID contactUserId);

    boolean existsByOwnerUserIdAndContactUserId(UUID ownerUserId, UUID contactUserId);
}
