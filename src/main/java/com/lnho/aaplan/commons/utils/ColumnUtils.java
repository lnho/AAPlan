package com.lnho.aaplan.commons.utils;



import com.lnho.aaplan.commons.mybatis.annotation.Id;
import com.lnho.aaplan.commons.mybatis.annotation.Table;

import java.lang.reflect.Field;

/**
 * User: wangj
 * Date: 13-11-11
 * Time: 下午5:12
 */
public class ColumnUtils {

    public final static <M> String getTableName(Class<M> clazz) {
        try {
            Table table = clazz.getAnnotation(Table.class);
            return table.value();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("table name not set!");
    }

    /**
     * 获取标记了id注解的属性名称
     * @param clazz
     * @param <M>
     * @return
     */
    public final static <M> String getIdFieldName(Class<M> clazz ) {
        for(Field field : clazz.getDeclaredFields()) {
            Id idField = field.getAnnotation(Id.class);
            if (idField == null) {
                continue;
            }
            return field.getName();
        }
        throw new RuntimeException("@id field not find ");
    }

}
