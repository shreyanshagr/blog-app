package com.sparrow.blog.service.impl;

import com.sparrow.blog.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Get filename
        String orgFileName = file.getOriginalFilename();

        //Generate RandomName
        String randomId = UUID.randomUUID().toString();
        assert orgFileName != null;
        String randomFileName = randomId.concat(orgFileName.substring(orgFileName.lastIndexOf('.')));

        //Create full Path
        String filePath = path + File.separator + randomFileName;

        //Create folder if not created
        File f = new File(path);
        if(!f.exists()) f.mkdirs();

        //Copy file to destination
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
         InputStream is = new FileInputStream(fullPath);
         // db logic to return inputStream
        return is;
    }
}
