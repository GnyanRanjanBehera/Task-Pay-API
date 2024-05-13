package com.task_pay.task_pay.services.impl;

import com.task_pay.task_pay.exceptions.BadApiRequestException;
import com.task_pay.task_pay.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :{}",originalFilename);
        String filename= UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=filename+extension;
        String fullPathWithFileName=path+ File.separator+fileNameWithExtension;
        if ((extension.equalsIgnoreCase(".png")) ||
                (extension.equalsIgnoreCase(".jpg")) ||
                (extension.equalsIgnoreCase(".jpeg")) ||
                (extension.equalsIgnoreCase(".pdf")) ||
                (extension.equalsIgnoreCase(".doc"))
        ){
            File folder=new File(path);
            if (!folder.exists()){
//                create folder
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return  fileNameWithExtension;
        }else {
            throw  new BadApiRequestException("File with this"+extension+"not allowed !!!");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        return new FileInputStream(fullPath);
    }

    @Override
    public void deleteImage(String path){
        try{
            Files.delete(Paths.get(path));
        }catch (NoSuchFileException ex){
            logger.info("User Image is not found in folder");
        }catch(IOException e){
            logger.info(e.toString());
        }
    }
}
