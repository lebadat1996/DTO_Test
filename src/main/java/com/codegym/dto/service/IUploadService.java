package com.codegym.dto.service;

import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    String uploadFile(MultipartFile multipartFile); // dung multipartFile

    User uploadFile(UserDto userDto) throws Exception; // dung base 64
}
