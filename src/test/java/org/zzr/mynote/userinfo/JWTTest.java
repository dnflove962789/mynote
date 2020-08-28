package org.zzr.mynote.userinfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.entity.UserInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JWTTest {

    @Test
    public void getJwt(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1001);
        userInfo.setEmail("abc@de.fg");
        userInfo.setType(1);
        //String jwtString = JwtUtils.getJwtString(userInfo);
        //System.out.println(jwtString);

    }

    @Test
    public void checkUser(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAxIiwic3ViIjoiVGhpc0lzTXlOb3RlIiwiaWF0IjoxNTkwNzM5MTc4LCJleHAiOjE1OTA3ODIzNzgsInVzZXJOYW1lIjoiYWJjQGRlLmZnIiwidXNlclR5cGUiOjF9._E-AKLqjgdMMu504pz24RLUALV6BnYDA3p7yL7d6AcQ";
        UserInfo userInfo = JwtUtils.getUserInfo(jwt);
        System.out.println(userInfo.getEmail());
    }
}
