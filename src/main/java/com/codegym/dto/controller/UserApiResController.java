package com.codegym.dto.controller;

import com.codegym.dto.UserMapper;
import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import com.codegym.dto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

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
    private Environment env;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> users = userMapper.toUserDTOs(userService.getAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@ModelAttribute User user) throws Exception {
        User user1 = new User(user.getUserName(), user.getFullName(), user.getEmail(), null);
        MultipartFile multipartFile = user.getImages();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpLoad = env.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(user.getImages().getBytes(), new File(fileUpLoad + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user1.setAvatar(fileName);
        User user2 = userService.create(user1);
        return new ResponseEntity<>(user2, HttpStatus.OK);
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
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        User user = userMapper.toUser(userDto);
        user.setId(id);
        user.setCreatedAt(userService.get(user.getId()).get().getCreatedAt());
        if (!userService.isDeleted(id)) {
            userService.update(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
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
