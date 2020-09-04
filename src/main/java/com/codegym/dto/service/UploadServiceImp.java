package com.codegym.dto.service;

import com.codegym.dto.dto.UserDto;
import com.codegym.dto.entity.User;

import com.codegym.dto.repository.UserRepository;
import com.codegym.dto.until.FnCommon;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImp implements IUploadService {
    @Value("${upload_path}")
    String uploadPath;

    @Autowired
    UserRepository userRepository;

    Environment env;

    @Autowired
    public UploadServiceImp(Environment env) {
        this.env = env;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String fileUpLoad = env.getProperty("file_upload");
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpLoad + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public String uploadFile(String imageValue, String fileName) throws IOException {
        byte[] imageByte = Base64.decodeBase64(imageValue);
        String filePath = uploadPath + UUID.randomUUID().toString() + File.separator + fileName;
        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file, imageByte);
        return file.getName();
    }

}
