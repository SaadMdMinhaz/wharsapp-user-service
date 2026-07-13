package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.AddContactRequest;
import com.whatsapp.userservice.dto.response.ContactResponse;
import com.whatsapp.userservice.entity.Contact;
import com.whatsapp.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Contact toEntity(AddContactRequest request);

    @Mapping(target = "contactUserId", source = "contact.contactUserId")
    @Mapping(target = "nickname", source = "contact.nickname")
    @Mapping(target = "displayName", source = "user.displayName")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "profilePictureUrl", source = "user.profilePictureUrl")
    @Mapping(target = "createdAt", source = "contact.createdAt")
    ContactResponse toResponse(Contact contact, User user);
}
