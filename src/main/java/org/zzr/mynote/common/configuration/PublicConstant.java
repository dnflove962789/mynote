package org.zzr.mynote.common.configuration;

public class PublicConstant {

    /**
     * 默认用户类型
     */
    public static final int DEFAULT_USER_TYPE = 0;

    /**
     * 管理员的用户类型
     */
    public static final int ADMIN_USER_TYPE = 100;

    /**
     *  是否正确的用户类型
     */
    public static boolean isUserType(Integer userType){
        if(userType == null){
            return false;
        }
        return userType == DEFAULT_USER_TYPE || userType == ADMIN_USER_TYPE;
    }

    /**
     * 邮件验证码有效期
     */
    public static final int EMAIL_CODE_TIME = 5;

    /**
     * 邮件验证码长度
     */
    public static final int EMAIL_CODE_LENGTH = 6;

    /**
     * 业务成功标示
     */
    public static final int SUCCESS  = 0;

    /**
     * 业务失败标示
     */
    public static final int FAILED = 1;

    /**
     * 应用名
     */
    public static String APP_NAME = "ThisIsMyNote" ;

    /**
     * 注册类型
     */
    public static final int REGISTER_TYPE = 0;

    /**
     * 登录类型
     */
    public static final int LOGIN_TYPE = 1;

    /**
     * 重置密码类型
     */
    public static final int RESET_PASSWORD_TYPE = 2;

    /**
     * 服务器访问地址
     */
    public static String serviceUrl;

    public static String nginxPath;

    public static String nginxUrl;

    public static String imgPath;

    public static String thumPath;

    public static String webUrl;

    public static final String USER_ID_KEY = "kellerUserId";

    public static final String ADMIN_ID_KEY = "kellerAdminId";

    /**
     * 应用启动的端口号
     */
    public static String port ;

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
}
