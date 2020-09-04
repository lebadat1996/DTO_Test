package com.codegym.dto.until;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FnCommon {
    public static boolean checkBriefcaseValid(String fileName, byte[] file, Integer maxFileSizeMb) {
        if (Objects.isNull(maxFileSizeMb)) {
            maxFileSizeMb = 5;
        }
        Objects.requireNonNull(file);
        long fileSizeMb = file.length / (1024 * 1024);
        return checkFileExtensionValid(fileName, ".JPG", ".PNG", ".TIFF", ".BMP", ".PDF") && fileSizeMb <= maxFileSizeMb;
    }
    public static boolean checkFileExtensionValid(String fileName, String... fileExtensions) {
        Objects.requireNonNull(fileName);
        for (String fileExtension : fileExtensions) {
            if (fileName.toLowerCase().endsWith(fileExtension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
