package com.ditto.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFIle(String uploadPath, String oname, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = oname.substring(oname.lastIndexOf("."));
        String sname =  uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + sname;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return sname;
    }
    
    public void deleteFile(String filepath) throws Exception {
        System.out.println("파일서비스 확인용");
        File deleteFile = new File(filepath);
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제 완료");
        } else {
            log.info("파일이 존재하지 않음");
        }
    }
}
