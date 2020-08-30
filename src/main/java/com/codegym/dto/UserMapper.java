package com.codegym.dto;

import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDTOs(List<User> users);

    User toUser(UserDto userDto);
}
