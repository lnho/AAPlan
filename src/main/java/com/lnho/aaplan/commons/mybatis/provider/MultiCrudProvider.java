package com.lnho.aaplan.commons.mybatis.provider;

import com.lnho.aaplan.commons.mybatis.annotation.MultiTable;
import com.lnho.aaplan.commons.mybatis.annotation.Table;
import com.lnho.aaplan.commons.mybatis.annotation.UnColumn;
import com.lnho.aaplan.commons.mybatis.bean.MultiQuery;
import com.lnho.aaplan.commons.mybatis.bean.Query;
import com.lnho.aaplan.commons.mybatis.util.ClassUtil;
import com.lnho.aaplan.commons.mybatis.util.ObjectUtil;
import com.lnho.aaplan.commons.mybatis.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * User: littlehui
 * Date: 14-5-7
 * Time: 上午11:44
 */
public class MultiCrudProvider<T> extends CrudProvider<T> {
    private static final Logger logger = LoggerFactory.getLogger(MultiCrudProvider.class);

    public String findByMultiQuery(MultiQuery<T> query) throws Exception {
        T obj = ObjectUtil.toBean(query.getType(), query.getParams());
        ObjectUtil.addToBean(obj, query.getType(), query.getParamLikes());
        ObjectUtil.addToBean(obj, query.getType(), query.getLtParams());
        ObjectUtil.addToBean(obj, query.getType(), query.getGtParams());
        return findPageByObject(obj, query);
    }


    protected String getColumnName(Field  field, boolean isDefault) {
        String columnName = super.getColumnName(field);
        String tableName = getColumnTable(field, isDefault, isDefault);
        if (StringUtil.isNotEmpty(tableName)) {
            return tableName + "." + columnName;
        }
        return columnName;
    }


    protected String getColumnName(Field  field) {
        return getColumnName(field, true);
    }



    protected List<String> buildWhereCommands (T obj ,Query<T> query) throws Exception {
        List<String> list = super.buildWhereCommands(obj, query);
        if (query instanceof MultiQuery)  {
            for(Field field : obj.getClass().getDeclaredFields()) {
                if (skipField(obj , field, (MultiQuery)query)) {
                    continue;
                }
                String columnCommand = buildFieldColumnCommand((MultiQuery) query, field);
                if (StringUtil.isNotEmpty(columnCommand)) {
                    list.add(buildFieldColumnCommand((MultiQuery) query, field));
                }
            }
        }
        return list;
    }

    protected boolean skipField(T obj ,Field field, MultiQuery query) {
        try {
            UnColumn unColumn = field.getAnnotation(UnColumn.class);
            if (unColumn != null) {
                return true;
            }
            if (!ClassUtil.isProperty(obj.getClass(), field.getName())) {
                return true;
            }
            String value = BeanUtils.getProperty(obj, field.getName());
            if (value == null && !hasFieldParams(field, query)) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean hasFieldParams(Field field, MultiQuery query) {
        Map<String , Object> fielEqparams = query.getFielEqparams();
        Map<String , Object> fielGtparams = query.getFielGtparams();
        Map<String , Object> fielLtparams = query.getFielLtparams();
        if (fielEqparams.containsKey(field.getName())
                || fielGtparams.containsKey(field.getName())
                || fielLtparams.containsKey(field.getName())) {
            return true;
        }
        return false;
    }


    protected String buildColumnCommand(Query query , Field field) {
        String expression = query.getExpression(field.getName());
        String objPrefix = query.getExpressionParam(field.getName());
        if ("field".equals(objPrefix)) {
            return "";
        }
        if (objPrefix == null) {
            return (getColumnName(field)  + expression +"#{" + field.getName() + "}");
        }else {
            return (getColumnName(field)  + expression +"#{" +objPrefix+"."+ field.getName() + "}");
        }
    }


    /**
     * 这个是处理field对field的方式比较粗糙。
     * @param query
     * @param field
     * @return
     */
    private String buildFieldColumnCommand(MultiQuery query , Field field) {
        String expression = query.getFieldExpression(field.getName());
        String fieldParams = query.getFieldExpressionParam(field.getName());
        if (MultiQuery.FIELD_EQ_PARAMS.equals(fieldParams)) {
            String fieldsName = (String)query.getFielEqparams().get(field.getName());
            if (field.getName() != null && field.getName().equals(fieldsName)) {
                //不同表，表字段相同进行关联查询
                return (getColumnName(field)  + expression +getColName(query.getType(), field.getName(), false));
            }
        } else if (MultiQuery.FIELD_GT_PARAMS.equals(fieldParams)) {
            String fieldsName = (String)query.getFielGtparams().get(field.getName());
            return (getColumnName(field)  + expression + getColName(query.getType(), fieldsName));
        } else if (MultiQuery.FIELD_LT_PARAMS.equals(fieldParams)) {
            String fieldsName = (String)query.getFielLtparams().get(field.getName());
            return (getColumnName(field)  + expression + getColName(query.getType(), fieldsName));
        }
        return "";
    }

    public  <M> String getColName(Class<M> clazz , String fieldName, boolean isDefault) {
        for(Field field : clazz.getDeclaredFields()) {
            String columnName = getColumnName(field, isDefault);
            if (fieldName == field.getName()) {
                return columnName;
            }
        }
        throw new RuntimeException("fieldName :" +fieldName+" not find ");
    }



   protected  String getColumnTable(Field field) {
       return  getColumnTable(field, true, true) ;
   }

    protected String getColumnTable(Field field, boolean isSign, boolean isDefault) {
        Table table = field.getAnnotation(Table.class);
        if (table != null) {
            return table.value();
        }
        MultiTable multiTable = field.getAnnotation(MultiTable.class);
        if (multiTable != null) {
            String sign = multiTable.sign();
            String values = multiTable.value();
            String[] tables = StringUtil.split(values, ',');
            if (!StringUtil.isEmpty(multiTable.sign()) && isSign) {
                return multiTable.sign();
            }
            if (tables.length > 0) {
                if (isDefault) {
                    return tables[0];
                }  else {
                    if (StringUtil.isNotEmpty(sign)) {
                        for (String ta : tables) {
                            if (!ta.equals(sign)) {
                                return ta;
                            }
                        }
                    }
                    return tables[tables.length - 1];
                }
            }
        }
        return "";
    }

    public String obtainTableName(Class<?> clazz) {
        MultiTable multiTable = clazz.getAnnotation(MultiTable.class);
        if (multiTable != null) {
            String tables = multiTable.value();
            return tables;
        } else {
            throw new RuntimeException("undefine POJO @MultiTable, need Tablename(@MultiTable)");
        }
    }

    /**
     * 执行统计,用于分页
     *
     * @param query
     * @return
     * @throws Exception
     */
    public String multiCount(MultiQuery<T> query) throws Exception {
        T obj = ObjectUtil.toBean(query.getType(), query.getParams());
        ObjectUtil.addToBean(obj, query.getType(), query.getParamLikes());
        ObjectUtil.addToBean(obj, query.getType(), query.getLtParams());
        ObjectUtil.addToBean(obj, query.getType(), query.getGtParams());
        ObjectUtil.addToBean(obj, query.getType(), query.getFielEqparams());
        ObjectUtil.addToBean(obj, query.getType(), query.getFielGtparams());
        ObjectUtil.addToBean(obj, query.getType(), query.getFielLtparams());
        return count(obj, query);
    }


    protected  String getSortColumnName(Field field) {
        String tableName = getColumnTable(field);
        if (StringUtil.isNotEmpty(super.getSortColumnName(field))) {
            if (StringUtil.isNotEmpty(tableName)) {
                return tableName + "." + super.getSortColumnName(field);
            }
        }
        return super.getSortColumnName(field);
    }
}
