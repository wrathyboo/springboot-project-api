package com.wrathyboo.api.service.impl;


import com.wrathyboo.api.service.FileUploadService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final Path storageFolder = Paths.get("C:\\Images");


    public FileUploadServiceImpl() throws Exception{
        // Creat storage folder if not exist
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException ioException){
            ioException.printStackTrace();
            throw new Exception(ioException);
        }
    }

    @Override
    public String storageFile(MultipartFile file) throws Exception {
        try {
            // Check file is empty
            if (file.isEmpty()){
                return "";
            }
            // Creat new file
            String filenameExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFilename = UUID.randomUUID().toString().replace("-","");
            generatedFilename = generatedFilename + "." + filenameExtension;
            Path destinationFilename = this.storageFolder.resolve(Paths.get(generatedFilename)).normalize().toAbsolutePath();
            // Save file
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,destinationFilename, StandardCopyOption.REPLACE_EXISTING);

            return generatedFilename;

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) throws Exception {
    	  try{
              Path file = storageFolder.resolve(fileName);
              Resource resource = new UrlResource(file.toUri());
              if (resource.exists() || resource.isReadable()){
                  return StreamUtils.copyToByteArray(resource.getInputStream());
              }else {
                  return null;
              }
          } catch (IOException e) {
              e.printStackTrace();
              return null;
          }
    }

    @Override
    public void deleteFile(String url) {
        File myObj = new File(storageFolder + "\\" + url);
        myObj.delete();
    }
}
