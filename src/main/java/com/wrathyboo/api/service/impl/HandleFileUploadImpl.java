package com.wrathyboo.api.service.impl;

import com.wrathyboo.api.service.HandleFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

@Service
public class HandleFileUploadImpl implements HandleFileUpload {
    private final String DIRECTORY = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String save(MultipartFile file) {
        try {
            // Tạo đường dẫn cho thư mục uploads
            String directory = DIRECTORY;

            // Tạo thư mục uploads nếu nó chưa tồn tại
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Tạo tệp mới trong thư mục uploads
            String filename = new Date().hashCode() + "-"+ file.getOriginalFilename();
            File newFile = new File(directory + filename);

            // Ghi dữ liệu từ MultipartFile vào tệp mới
            file.transferTo(newFile);

            // đường dẫn tệp
            return directory + filename;
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public Boolean delete(String pathFile) {
        try{
            File image = new File(pathFile);
            return image.delete();
        }catch (Exception ignored){
            return false;
        }
    }

    @Override
    public byte[] getImage(String pathFile) {
        try {
            File f = new File(pathFile);
            return Files.readAllBytes(f.toPath());
        }catch (Exception ignored){
            return null;
        }
    }
}
