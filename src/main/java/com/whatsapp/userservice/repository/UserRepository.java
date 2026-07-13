package com.whatsapp.userservice.repository;

import com.whatsapp.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    List<User> findByDisplayNameContainingIgnoreCase(String displayName);

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByPhoneNumberContaining(String phoneNumber);

    List<User> findByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String displayName, String username);
}
