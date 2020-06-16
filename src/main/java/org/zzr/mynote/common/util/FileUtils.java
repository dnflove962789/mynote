package org.zzr.mynote.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.zzr.mynote.common.configuration.PublicConstant;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    /**
     * 文件后缀名分隔符
     */
    public static final String SUFFIX_SPLIT = ".";

    /**
     * 缩略图前缀名
     */
    public static final String THUM_PREFIX = "thum";

    /**
     * 缩略图最大宽度
     */
    public static final int THUM_MAX_WIDTH = 120;

    /**
     * 缩略图最大高度
     */
    public static final int THUM_MAX_HEIGHT = 120;

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.indexOf(SUFFIX_SPLIT) +1);
    }

    

    /**
     * 获取原图保存路径
     * @param fileName 文件名
     * @return 完整的保存路径
     */
    public static String getImgPath(String fileName){
        return PublicConstant.nginxPath + PublicConstant.imgPath + fileName;
    }

    /**
     * 获取原图访问地址
     * @param fileName
     * @return
     */
    public static String getImgUrl(String fileName){
        return PublicConstant.nginxUrl + PublicConstant.imgPath + fileName;
    }

    /**
     * 获取缩略图保存路径
     * @param fileName 文件名
     * @return 完整的保存路径
     */
    public static String getThumPath(String fileName){
        return PublicConstant.nginxPath + PublicConstant.thumPath + THUM_PREFIX + fileName;
    }

    /**
     *获取压缩图访问地址
     * @param fileName
     * @return
     */
    public  static String getThumUrl(String fileName){
        return PublicConstant.nginxUrl + PublicConstant.thumPath + THUM_PREFIX + fileName;
    }

    /**
     * 保存图片
     * @param file
     * @param name
     * @return
     */
    public static String saveImg(MultipartFile file, String name)  {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(SUFFIX_SPLIT));
        //构建原图保存路径
        String fileName = name + suffix;
        //保存原图
        File img = new File(FileUtils.getImgPath(fileName));
        try {
            file.transferTo(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 保存图片和缩略图
     * @param file
     * @param name
     * @return
     * @throws IOException
     */
    public static String saveImgAndThum(MultipartFile file, String name)  {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(SUFFIX_SPLIT));
        //构建原图保存路径
        String fileName = name + suffix;
        //保存原图
        File img = new File(FileUtils.getImgPath(fileName));
        try {
            file.transferTo(img);

            //保存缩略图
            File thum = new File(getThumPath(fileName));
            Thumbnails.of(img).size(THUM_MAX_WIDTH, THUM_MAX_HEIGHT).toFile(thum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }


}
