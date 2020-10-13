package org.zzr.mynote.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {

    private static String USER_NAME_KEY = "userName";
    private static String USER_NAME_TYPE = "userType";
    private static String typeKey = "type";
    private static String codeKey = "code";
    /**
     * JWT 有效时间 12 小时
     */
    public static final long JWT_EXP_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成token
     * @param userInfo
     * @param type
     * @param code
     * @return
     */
    public static String getToken(UserInfo userInfo, int type , String code) {
        long now = System.currentTimeMillis();
        String token="";
        token= JWT.create().withAudience(userInfo.getId().toString())// 将 user id 保存到 token 里面
                .withSubject(PublicConstant.appName)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + JWT_EXP_TIME))
                .withClaim(USER_NAME_KEY, userInfo.getEmail())
                .withClaim(USER_NAME_TYPE, userInfo.getType())
                .withClaim(typeKey,type)
                .withClaim(codeKey,code)
                .sign(Algorithm.HMAC256(userInfo.getPassword()));//密钥;
        return token;
    }

    public static String getTokenForLogin(UserInfo userInfo){
        return getToken(userInfo,PublicConstant.LOGIN_TYPE,null);
    }

    public static String getTokenForResetPassword(UserInfo userInfo,String code){
        return getToken(userInfo,PublicConstant.RESET_PASSWORD_TYPE,code);
    }

    /**
     * 根据token解析出userid
     * @param request
     * @return
     */
    public static Integer getUserIdFromRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        return userId;
    }

    /**
     * 根据token解析出userid(用于admin)
     * @param request
     * @return
     */
    public static Integer getAdminUserIdFromRequest(HttpServletRequest request){
        String token = request.getHeader("adminToken");
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        return userId;
    }
}
