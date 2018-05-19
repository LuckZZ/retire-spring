package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public class FileUtils {

    /**
     * 上传文件
     * @param file
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void uploadFile(MultipartFile file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        File dest = new File(filePath+fileName);
//        保存文件
//        file.transferTo(dest);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        out.write(file.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return png、jpg等
     */
    public static String getFileExtName(String fileName) {
        if (fileName!=null ) {
            int i = fileName.lastIndexOf('.');
            if (i>-1) {
                return fileName.substring(i+1).toLowerCase();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
