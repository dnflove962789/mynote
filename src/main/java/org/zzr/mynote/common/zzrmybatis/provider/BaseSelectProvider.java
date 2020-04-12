package org.zzr.mynote.common.zzrmybatis.provider;

import org.zzr.mynote.common.zzrmybatis.BaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:BaseSelectProvider
 * Version:2020/4/12 & 1.0
 */
public class BaseSelectProvider {
    public static Map<String,String> selectPreFixMap = new ConcurrentHashMap<>(16);

    public static <T extends BaseEntity> String selectAll(T entity){
        String sql = getSelectPreFix(entity);
        return sql;
    }

    private static <T extends BaseEntity> String getSelectPreFix(T entity) {
        String className = entity.getClass().getName();
        String sql;
        sql = selectPreFixMap.get(className);
        if(sql == null){
            sql = "SELECT "+ SqlInfoReader.getFiledsString(entity) +" FROM "+ SqlInfoReader.getTableName(entity);
            selectPreFixMap.put(className, sql);
        }
        return sql;
    }
}
