package org.zzr.mynote.common.zzrmybatis.provider;

import org.zzr.mynote.common.zzrmybatis.BaseEntity;
import org.zzr.mynote.common.zzrmybatis.annoation.FieldAttribute;
import org.zzr.mynote.common.zzrmybatis.annoation.TableAttribute;

import java.lang.reflect.Field;

/**
 * Author:SqlFiledReader
 * Version:2020/4/12 & 1.0
 */
public class SqlInfoReader {

    /**
     * 读取类的注解中的表名
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String getTableName(T entity){
        TableAttribute table = entity.getClass().getAnnotation(TableAttribute.class);
        if(table == null){
            return entity.getClass().getName();
        }
        return table.name();
    }

    /**
     * 读取类中的字段，然后拼装，用于查询的字段
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String getFiledsString(T entity){
        Class<? extends BaseEntity> cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for(Field field:fields){
            //判断是否有注解，然后读取字段拼装
            FieldAttribute fa = field.getAnnotation(FieldAttribute.class);
            if(fa != null){
                builder.append(fa.name()).append(",");
            }else{
                builder.append(field.getName()).append(",");
            }
        }
        if(builder.length()>0){
            return builder.substring(0,builder.length()-1);
        }
        return null;
    }
}
