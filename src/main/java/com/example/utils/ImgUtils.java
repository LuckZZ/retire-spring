package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

public class ImgUtils {

    private String jobnum;
    private MultipartFile file;

    public ImgUtils(String jobnum, MultipartFile file) {
        this.jobnum = jobnum;
        this.file = file;
    }

    public void save(){
//       上传时的文件名
        String fileName = file.getOriginalFilename();

        String newName ="/img/"+jobnum+getFilePostfix(fileName);

        System.out.println("newName:"+newName);
    }

    /**
     * 得到文件的后缀
     * @param fileName
     * @return
     */
    private String getFilePostfix(String fileName) {
        String filePostfix = null;
        try {
            filePostfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        } catch (Exception e) {
//			出现异常
            filePostfix = null;
        }
        return filePostfix;
    }

}
