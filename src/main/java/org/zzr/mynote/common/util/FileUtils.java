package org.zzr.mynote.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.zzr.mynote.common.configuration.CommonConfig;
import org.zzr.mynote.common.configuration.PublicConstant;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {
    /**
     * 文件后缀名分隔符
     */
    public static final String SUFFIX_SPLIT = ".";

    /**
     * 获取文件名的后缀，如 jpg/txt等
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf(SUFFIX_SPLIT) + 1);
    }
    /**
     * 获取文件名的后缀和分隔符，如 .jpg/.txt等
     * @param fileName
     * @return
     */
    public static String getSuffixWithSpilt(String fileName){
        return fileName.substring(fileName.lastIndexOf(SUFFIX_SPLIT));
    }



    /**
     * 获取图片保存路径
     * @param fileName 文件名
     * @return 完整的保存路径
     */
    public static String getImgPath(String fileName, int userId){
        String directory = getUserImgDirectory(userId, PublicConstant.NOTE_PREFIX);
        if(directory.isEmpty()){
            return null;
        }else {
            return directory + fileName;
        }
    }

    /**
     * 获取头像保存路径
     * @param fileName 文件名
     * @return 完整的保存路径
     */
    public static String getPortraitPath(String fileName, int userId){
        String directory = getUserImgDirectory(userId, PublicConstant.PORTRAIT_PREFIX);
        if(directory.isEmpty()){
            return null;
        }else {
            return directory + fileName;
        }
    }

    /**
     * 获取头像略缩图保存路径
     * @param fileName 文件名
     * @return 完整的保存路径
     */
    public static String getPortraitThumPath(String fileName, int userId){
        String directory = getUserImgDirectory(userId, PublicConstant.PORTRAIT_PREFIX);
        if(directory.isEmpty()){
            return null;
        }else {
            //略缩图特殊前缀
            return directory + PublicConstant.THUM_PREFIX +"_"+fileName;
        }
    }

    /**
     * 获取用户笔记图片目录
     * @param userId
     * @return
     */
    private static String getUserImgDirectory(int userId, String perfix){
        StringBuilder builder = new StringBuilder();
        builder.append(PublicConstant.localImgPath)
                .append(File.separator)
                .append(userId)
                .append(File.separator)
                .append(perfix)
                .append(File.separator);
        if(mkdirs(builder.toString())){
            return builder.toString();
        }else{
            return null;
        }
    }

    /**
     * 获取头像文件名
     * @param userId
     * @return 文件名，不带后缀
     */
    public static String getPortraitName(int userId){
        return Md5Utils.getMd5String(PublicConstant.PORTRAIT_PREFIX + userId) ;
    }



    /**
     * 获取头像保存路径
     * @param fileName 文件名，不带后缀
     * @return  完整的头像保存路径
     */
    public static String getPortraitFile(String fileName, int userId){
        StringBuilder builder = new StringBuilder();
        builder.append(PublicConstant.localImgPath)
                .append(File.separator)
                .append(userId)
                .append(File.separator)
                .append(PublicConstant.PORTRAIT_PREFIX);

        if(mkdirs(builder.toString())){
            /*builder.append(fileName)
                    .append(".")
                    .append(PublicConstant.PORTRAIT_TYPE);*/
            return builder.toString();
        }else {
            return null;
        }
    }


    /**
     * 获取原图访问地址
     * @param fileName
     * @return
     */
    public static String getImgUrl(String fileName){
        //return PublicConstant.nginxUrl + PublicConstant.imgPath + fileName;
        return PublicConstant.webImgPath + fileName;

    }


    /**
     * 创建多级目录
     * @param path
     * @return
     */
    private static boolean mkdirs(String path){
        File directory = new File(path);
        if(directory.exists() && directory.isDirectory()){
            return true;
        }else {
            return directory.mkdirs();
        }
    }


    /**
     * 获取头像缩略图访问地址
     * @param fileName
     * @return
     */
    public static String getPortraitUrl(String fileName, Integer userId){
        StringBuilder builder = new StringBuilder();
        builder.append(PublicConstant.webImgPath)
                .append(userId)
                .append("/")
                .append(PublicConstant.PORTRAIT_PREFIX)
                .append("/")
                .append(fileName);
        return builder.toString();
    }

    /**
     * 获取头像缩略图访问地址
     * @param fileName
     * @return
     */
    public static String getPortraitThumUrl(String fileName, Integer userId){
        StringBuilder builder = new StringBuilder();
        builder.append(PublicConstant.webImgPath)
                .append(userId)
                .append("/")
                .append(PublicConstant.PORTRAIT_PREFIX)
                .append("/")
                //略缩图有前缀
                .append(PublicConstant.THUM_PREFIX+"_")
                .append(fileName);
        return builder.toString();
    }

    /**
     * 获取笔记图片访问地址
     * @param fileName
     * @return
     */
    public static String getNoteUrl(String fileName, Integer userId){
        StringBuilder builder = new StringBuilder();
        builder.append(PublicConstant.webImgPath)
                .append(userId)
                .append("/")
                .append(PublicConstant.NOTE_PREFIX)
                .append("/")
                .append(fileName);
        return builder.toString();
    }
}
