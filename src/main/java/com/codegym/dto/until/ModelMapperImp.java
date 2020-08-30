package com.codegym.dto.until;

import com.codegym.dto.UserMapper;
import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperImp implements UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> toUserDTOs(List<User> users) {
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public User toUser(UserDto userDto) {

        return modelMapper.map(userDto, User.class);
    }
}
