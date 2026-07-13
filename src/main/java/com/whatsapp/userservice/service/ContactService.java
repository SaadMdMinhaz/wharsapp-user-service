package com.whatsapp.userservice.service;

import com.whatsapp.userservice.dto.request.AddContactRequest;
import com.whatsapp.userservice.dto.request.RenameContactRequest;
import com.whatsapp.userservice.dto.response.ContactResponse;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    /**
     * Retrieves all contacts for a given user.
     *
     * @param ownerUserId the owner user UUID
     * @return list of contact responses
     */
    List<ContactResponse> getContacts(UUID ownerUserId);

    /**
     * Adds a new contact for the specified user.
     *
     * @param ownerUserId the owner user UUID
     * @param request     the add contact request
     * @return the created contact response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if contact user not found
     * @throws com.whatsapp.userservice.exception.ContactAlreadyExistsException if contact already exists
     */
    ContactResponse addContact(UUID ownerUserId, AddContactRequest request);

    /**
     * Renames an existing contact.
     *
     * @param ownerUserId the owner user UUID
     * @param contactId   the contact UUID
     * @param request     the rename request
     * @return the updated contact response
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if contact not found
     */
    ContactResponse renameContact(UUID ownerUserId, UUID contactId, RenameContactRequest request);

    /**
     * Removes a contact from the user's contact list.
     *
     * @param ownerUserId the owner user UUID
     * @param contactId   the contact UUID
     * @throws com.whatsapp.userservice.exception.UserNotFoundException if contact not found
     */
    void deleteContact(UUID ownerUserId, UUID contactId);
}
