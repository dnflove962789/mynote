package org.zzr.mynote.common.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Author:MyMetaObjectHandler
 * Version:2020/5/2 & 1.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("createTime")){
            log.info("start insert fill ....");
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //注意只有updatebyid和update（entity,Wrapper)方法可以触发，其他的lambda不会触发
        //表示是否有setter字段
        if(metaObject.hasSetter("updateTime")) {
            //表示是否有自己手动set值进去
            if(getFieldValByName("updateTime", metaObject)==null){
                log.info("start update fill ....");
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }
        }
    }


}
