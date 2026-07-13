package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.UpdatePrivacyRequest;
import com.whatsapp.userservice.dto.response.PrivacyResponse;
import com.whatsapp.userservice.entity.UserPrivacy;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PrivacyMapper {

    @Mapping(target = "userId", ignore = true)
    UserPrivacy toEntity(UpdatePrivacyRequest request);

    PrivacyResponse toResponse(UserPrivacy privacy);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", ignore = true)
    void updateEntity(UpdatePrivacyRequest request, @MappingTarget UserPrivacy privacy);
}
