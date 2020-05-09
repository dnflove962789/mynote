package org.zzr.mynote.common.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String numbersChar = "0123456789";
    public static final String allChar = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";

    /**
     * 邮箱校验规则：**@**.**
     */
    private static final String EMAIL_ADDRESS_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|" +
            "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    /**
     * 判断一系列字符串中是否有空的（包含:空字符串、null、纯空格字符）
     * @param parameters 需要判断的字符串，可以是多个
     * @return
     */
    public static boolean isEmpty(String... parameters){
        for(String str:parameters){
            if(str == null || str.isEmpty() || str.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }
    public static boolean isNotEmpty(String... parameters){
        return !isEmpty(parameters);
    }

    /**
     * 验证邮箱是否正确
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if(isEmpty(email)){
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    /**
     * 生成指定长度的数字字符串
     * @param length
     *  字符串长度
     * @return
     */
    public static String getNumbserString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numbersChar.charAt(random.nextInt(numbersChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成指定长度的数字字母字符串
     * @param length
     *  字符串长度
     * @return
     */
    public static String getAllCharString(int length){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

}
