package org.zzr.mynote.common.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.entity.UserInfo;
import java.util.Date;

@Slf4j
public class JwtUtils {
    private static String USER_NAME_KEY = "userName";
    private static String USER_NAME_TYPE = "userType";
    private static String typeKey = "type";
    private static String codeKey = "code";
    /**
     * JWT 有效时间 12 小时
     */
    public static final long JWT_EXP_TIME = 12 * 60 * 60 * 1000;

    /**
     * JWT 签名
     */
    public static final String JWT_SIGN_KEY = "mynote1024";

    /**
     * 获取登录的 JWT
     * @param userInfo
     * @return
     */
    public static String getJwtForLogin(UserInfo userInfo){
        return getJwtString(userInfo,PublicConstant.LOGIN_TYPE,null);
    }

    public static String getJwtForResetPassword(UserInfo userInfo,String code){
        return getJwtString(userInfo,PublicConstant.RESET_PASSWORD_TYPE,code);
    }

    /**
     * 生产JWT
     * @param userInfo
     * @return
     */
    public static String getJwtString(UserInfo userInfo, int type, String code){
        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(userInfo.getId() + "")
                //设置应用名
                .setSubject(PublicConstant.APP_NAME)
                //签发时间
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(now + JWT_EXP_TIME))
                //自定义内容
                .claim(USER_NAME_KEY, userInfo.getEmail())
                .claim(USER_NAME_TYPE, userInfo.getType())
                .claim(typeKey,type)
                //签名，密匙
                .signWith(SignatureAlgorithm.HS256, JWT_SIGN_KEY);
        if(type == PublicConstant.RESET_PASSWORD_TYPE){
            jwtBuilder.claim(codeKey,code);
        }
        return jwtBuilder.compact();
    }

    /**
     * 从jwt中获取Userinfo
     * @param jwtString
     * @return
     */
    public static UserInfo getUserInfo(String jwtString){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SIGN_KEY)
                    .parseClaimsJws(jwtString)
                    .getBody();
            int id = Integer.parseInt(claims.getId());
            String subject = claims.getSubject();
            //校验应用名
            if(!subject.equals(PublicConstant.APP_NAME)){
                return null;
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setEmail(claims.get(USER_NAME_KEY, String.class));
            userInfo.setType(claims.get(USER_NAME_TYPE,Integer.class));
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("jwt解析失败");
            log.error("jwt解析失败:"+e);
            return null;
        }
    }

    /**
     *
     * @param jwtString
     * @param type
     * @return
     */
    private static Claims getClaims(String jwtString,int type){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SIGN_KEY)
                    .parseClaimsJws(jwtString)
                    .getBody();
            String subject = claims.getSubject();
            //校验应用名
            if(!subject.equals(PublicConstant.APP_NAME)){
                return null;
            }
            //校验 JWT 类型
            int jwtType = claims.get(typeKey,Integer.class);
            if(jwtType != type){
                return null;
            }
            return claims;
        }catch (Exception e){
            Console.error("checkJwt","JWT 解析失败",jwtString,e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT中获取到UserId
     * @param jwtString
     * @return
     */
    public static Integer getUserIdForLogin(String jwtString){
        Claims claims = getClaims(jwtString, PublicConstant.LOGIN_TYPE);
        if(claims != null){
            return Integer.parseInt(claims.getId());
        }else{
            return null;
        }
    }

    /**
     * 解析jwt重置0密码用到的code
     * @param jwtString
     * @return
     */
    public static String getCodeForResetPassword(String jwtString){
        Claims claims = getClaims(jwtString,PublicConstant.RESET_PASSWORD_TYPE);
        if(claims == null){
            return null;
        }
        return claims.get(codeKey,String.class);
    }
}
