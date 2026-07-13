package com.whatsapp.userservice.service.impl;

import com.whatsapp.userservice.dto.request.AddContactRequest;
import com.whatsapp.userservice.dto.request.RenameContactRequest;
import com.whatsapp.userservice.dto.response.ContactResponse;
import com.whatsapp.userservice.entity.Contact;
import com.whatsapp.userservice.entity.User;
import com.whatsapp.userservice.exception.ContactAlreadyExistsException;
import com.whatsapp.userservice.exception.UserNotFoundException;
import com.whatsapp.userservice.mapper.ContactMapper;
import com.whatsapp.userservice.repository.ContactRepository;
import com.whatsapp.userservice.repository.UserRepository;
import com.whatsapp.userservice.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository,
                             UserRepository userRepository,
                             ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getContacts(UUID ownerUserId) {
        log.debug("Fetching contacts for user: {}", ownerUserId);
        List<Contact> contacts = contactRepository.findByOwnerUserId(ownerUserId);
        return contacts.stream()
                .map(contact -> {
                    User contactUser = userRepository.findById(contact.getContactUserId())
                            .orElse(null);
                    return contactMapper.toResponse(contact, contactUser);
                })
                .toList();
    }

    @Override
    public ContactResponse addContact(UUID ownerUserId, AddContactRequest request) {
        log.info("Adding contact {} for user: {}", request.contactUserId(), ownerUserId);

        if (!userRepository.existsById(request.contactUserId())) {
            log.error("Contact user not found: {}", request.contactUserId());
            throw new UserNotFoundException("Contact user not found with id: " + request.contactUserId());
        }

        if (contactRepository.existsByOwnerUserIdAndContactUserId(ownerUserId, request.contactUserId())) {
            log.warn("Contact already exists: {} for user: {}", request.contactUserId(), ownerUserId);
            throw new ContactAlreadyExistsException("Contact already exists");
        }

        Contact contact = contactMapper.toEntity(request);
        contact.setOwnerUserId(ownerUserId);
        contact = contactRepository.save(contact);

        User contactUser = userRepository.findById(contact.getContactUserId())
                .orElse(null);

        log.info("Contact added with id: {}", contact.getId());
        return contactMapper.toResponse(contact, contactUser);
    }

    @Override
    public ContactResponse renameContact(UUID ownerUserId, UUID contactId, RenameContactRequest request) {
        log.info("Renaming contact {} for user: {}", contactId, ownerUserId);
        Contact contact = findContactOrThrow(ownerUserId, contactId);
        contact.setNickname(request.nickname());
        contact = contactRepository.save(contact);

        User contactUser = userRepository.findById(contact.getContactUserId())
                .orElse(null);

        log.info("Contact renamed: {}", contactId);
        return contactMapper.toResponse(contact, contactUser);
    }

    @Override
    public void deleteContact(UUID ownerUserId, UUID contactId) {
        log.info("Deleting contact {} for user: {}", contactId, ownerUserId);
        Contact contact = findContactOrThrow(ownerUserId, contactId);
        contactRepository.delete(contact);
        log.info("Contact deleted: {}", contactId);
    }

    private Contact findContactOrThrow(UUID ownerUserId, UUID contactId) {
        return contactRepository.findById(contactId)
                .filter(contact -> contact.getOwnerUserId().equals(ownerUserId))
                .orElseThrow(() -> {
                    log.error("Contact not found with id: {} for user: {}", contactId, ownerUserId);
                    return new UserNotFoundException("Contact not found with id: " + contactId);
                });
    }
}
