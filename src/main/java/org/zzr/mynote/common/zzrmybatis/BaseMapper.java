package org.zzr.mynote.common.zzrmybatis;

import org.apache.ibatis.annotations.SelectProvider;
import org.zzr.mynote.common.zzrmybatis.provider.BaseSelectProvider;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {

    @SelectProvider(type = BaseSelectProvider.class, method = "selectAll")
    List<T> baseSelectAll(T entity);
}
