package org.zzr.mynote.userinfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzr.mynote.entity.EmailLog;

import java.time.LocalDateTime;

/**
 * Author:ARTest
 * Version:2020/5/1 & 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    @Test
    public void insertEmailLog(){
        EmailLog emailLog = new EmailLog();
        emailLog.setType(0);
        emailLog.setEmail("avc@a.c");
        emailLog.setTitle("test");
        emailLog.setContent("testtest");
        emailLog.setStatusCode(0);
        emailLog.setCreateTime(LocalDateTime.now());

        boolean insert = emailLog.insert();
        System.out.println(insert);
    }

    @Test
    public void selectEmailLog(){
        EmailLog emailLog = new EmailLog();

        emailLog.setId(1);

        EmailLog emailLog1 = emailLog.selectById();
        System.out.println(emailLog1);
    }

    @Test
    public void updateOrInsert(){
        //有id就更新，没有就插入
        EmailLog emailLog = new EmailLog();
        emailLog.setId(1);
        emailLog.setEmail("agg@r");
        boolean ok = emailLog.insertOrUpdate();
        System.out.println(ok);
    }
}
