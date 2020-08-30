package com.codegym.dto.until;

import com.codegym.dto.UserMapper;
import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import com.codegym.dto.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
public class UserMapperImp implements UserMapper {
    @Override
    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPassword(user.getPassword());
        userDto.setUserName(user.getUserName());
        return userDto;
    }

    @Override
    public List<UserDto> toUserDTOs(List<User> users) {
        return users.stream().map(user -> toUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public User toUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
