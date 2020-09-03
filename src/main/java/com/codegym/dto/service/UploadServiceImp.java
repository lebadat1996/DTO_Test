package com.codegym.dto.service;

import org.apache.tomcat.jni.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadServiceImp implements IUploadService {

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
    public String uploadFile(String imageValue) throws IOException {
        byte[] imageByte = Base64.decodeBase64(imageValue);
        String fileUpLoad = env.getProperty("file_upload");
        File file = new File(fileUpLoad + "images.png");
        if (!file.exists()) {
            file.createNewFile();
        }
        new FileOutputStream(file).write(imageByte);
        return file.getAbsolutePath();
    }
}
