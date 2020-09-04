package com.codegym.dto.service;

import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    String uploadFile(MultipartFile multipartFile); // dung multipartFile

    String uploadFile(String imageValue, String fileName) throws Exception; // dung base 64
}
