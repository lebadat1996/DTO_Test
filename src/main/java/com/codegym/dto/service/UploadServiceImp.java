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
    public User uploadFile(UserDto userDto) throws Exception {
        if (!Base64.isBase64(userDto.getAvatar())) {
            throw new Exception("file không đúng định dạng base64");
        }
        byte[] file = Base64.decodeBase64(userDto.getAvatar());
        String filePath = uploadFolder + UUID.randomUUID().toString() + File.separator + userDto.getUserName();
        if (!FnCommon.checkBriefcaseValid(filePath, file, briefcaseMaxFileSize)) {
            throw new Exception("file không đúng size");
        }
        FileUtils.writeByteArrayToFile(new File(filePath), file);
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        return userRepository.save(user);
    }


}
