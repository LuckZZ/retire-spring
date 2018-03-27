package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImgUtils {

    private String jobnum;
    private MultipartFile file;

    public ImgUtils(String jobnum, MultipartFile file) {
        this.jobnum = jobnum;
        this.file = file;
    }

    public void save() throws IOException {
//       上传时的文件名
        String fileName = file.getOriginalFilename();
//        新的文件名
        String newName ="/img/"+jobnum+getFilePostfix(fileName);
        File dest = new File(newName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
//        保存文件
        file.transferTo(dest);
    }

    /**
     * 得到文件的后缀
     * @param fileName
     * @return
     */
    private String getFilePostfix(String fileName) {
        String filePostfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return filePostfix;
    }

}
