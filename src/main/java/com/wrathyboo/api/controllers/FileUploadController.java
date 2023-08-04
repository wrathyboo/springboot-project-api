package com.wrathyboo.api.controllers;

import com.wrathyboo.api.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/uploads")
@CrossOrigin
public class FileUploadController {
    @Autowired
    public FileUploadService fileUploadService;
    @Autowired
    public FileUploadController(FileUploadService fileUploadService){
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload (@RequestBody MultipartFile file) throws Exception{
    	   System.out.println("------------------" + file + "------------------");
            String generateFilename = fileUploadService.storageFile(file);
            return ResponseEntity.status(201).body(generateFilename);
    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = fileUploadService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
}
