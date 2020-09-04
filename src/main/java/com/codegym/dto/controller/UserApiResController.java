package com.codegym.dto.controller;

import com.codegym.dto.UserMapper;
import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import com.codegym.dto.service.IUploadService;
import com.codegym.dto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserApiResController {
    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserApiResController(IUserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }


    @Autowired
    private IUploadService uploadService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> users = userMapper.toUserDTOs(userService.getAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        User user = userMapper.toUser(userDto);
//        User entity = new User(user.getUserName(), user.getFullName(), user.getEmail(), null);
        String fileName = userDto.getAvatars().getFileName();
        String filePath = uploadService.uploadFile(userDto.getAvatars().getFileBase64(), fileName);
        user.setAvatar(filePath);
        User userEntity = userService.create(user);
        return new ResponseEntity<>(userMapper.toUserDto(userEntity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> finUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.get(id);
            return new ResponseEntity<>(userMapper.toUserDto(user.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @ModelAttribute UserDto userDto) throws Exception {
        User user = userMapper.toUser(userDto);
        user.setId(id);
        user.setCreatedAt(userService.get(user.getId()).get().getCreatedAt());
        if (!userService.isDeleted(id)) {
            User entity = userService.get(id).get();
            String fileName = uploadService.uploadFile(user.getImages());
            entity.setAvatar(fileName);
            User userEntity = userService.update(entity);
            return new ResponseEntity<>(userMapper.toUserDto(userEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
