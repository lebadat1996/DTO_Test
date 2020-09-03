package com.codegym.dto.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    String uploadFile(MultipartFile multipartFile); // dung multipartFile

    String uploadFile(String imageValue) throws IOException; // dung base 64
}
