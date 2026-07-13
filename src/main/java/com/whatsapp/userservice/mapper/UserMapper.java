package com.whatsapp.userservice.mapper;

import com.whatsapp.userservice.dto.request.CreateUserRequest;
import com.whatsapp.userservice.dto.request.UpdateUserRequest;
import com.whatsapp.userservice.dto.response.SearchUserResponse;
import com.whatsapp.userservice.dto.response.UserResponse;
import com.whatsapp.userservice.dto.response.UserSummaryResponse;
import com.whatsapp.userservice.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);

    UserSummaryResponse toSummaryResponse(User user);

    SearchUserResponse toSearchResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}
