package com.wrathyboo.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    String storageFile (MultipartFile file) throws Exception;
    byte[] readFileContent(String fileName) throws Exception;
    public void deleteFile(String url);
}
