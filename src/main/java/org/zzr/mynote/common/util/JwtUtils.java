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
    /**
     * JWT 有效时间 12 小时
     */
    public static final long JWT_EXP_TIME = 12 * 60 * 60 * 1000;

    /**
     * JWT 签名
     */
    public static final String JWT_SIGN_KEY = "mynote1024";

    /**
     * 生产JWT
     * @param userInfo
     * @return
     */
    public static String getJwtString(UserInfo userInfo){
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
                //签名，密匙
                .signWith(SignatureAlgorithm.HS256, JWT_SIGN_KEY);
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
     * @return
     */
    public static Integer getUserId(String jwtString){
        try{
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
            return id;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("jwt解析失败");
            log.error("jwt解析失败:"+e);
            return null;
        }
    }
}
