package com.lnho.aaplan.commons.mybatis.bean;

import com.lnho.aaplan.commons.mybatis.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * User: littlehui
 * Date: 14-5-7
 * Time: 下午4:46
 */
public class MultiQuery<T> extends Query<T> {
    public final  static String FIELD_EQ_PARAMS = "fielEqparams";
    public final  static String FIELD_GT_PARAMS = "fielGtparams";
    public final  static String FIELD_LT_PARAMS = "fielLtparams";

    public MultiQuery(Class<T> type) {
        super(type);
    }

    Map<String , Object> fielEqparams = new HashMap<String, Object>();
    Map<String , Object> fielGtparams = new HashMap<String, Object>();
    Map<String , Object> fielLtparams = new HashMap<String, Object>();

    /**
     * 联合查询需要用到的字段值相等
     * @param fieldSName
     * @param fieldRName
     * @return
     */
    public MultiQuery addFieldEq(String fieldSName, String fieldRName) {
        if (StringUtil.isEmpty(fieldSName) || StringUtil.isEmpty(fieldRName)) {
            return this;
        }
        fielEqparams.put(fieldSName, fieldRName);
        return this;
    }

    /**
     * 关联查询需要用到的字段值相等
     * @param fieldSName
     * @return
     */
    public MultiQuery addFieldEq(String fieldSName) {
        if (StringUtil.isEmpty(fieldSName)) {
            return this;
        }
        fielEqparams.put(fieldSName, fieldSName);
        return this;
    }


    public MultiQuery addFieldGt(String fieldSName, String fieldRName) {
        if (StringUtil.isEmpty(fieldSName) || StringUtil.isEmpty(fieldRName)) {
            return this;
        }
        fielGtparams.put(fieldSName, fieldRName);
        return this;
    }

    /**
     * 值之间
     * @param fieldSName
     * @param fieldRName
     * @return
     */
    public MultiQuery addFieldLt(String fieldSName, String fieldRName) {
        if (StringUtil.isEmpty(fieldSName) || StringUtil.isEmpty(fieldRName)) {
            return this;
        }
        fielLtparams.put(fieldSName, fieldRName);
        return this;
    }



    public  static MultiQuery build(Class<?> clazz) {
        return new MultiQuery(clazz);
    }


    public String getFieldExpression(String fieldName) {
        if (fielEqparams.containsKey(fieldName)) {
            return "=";
        }
        if (fielLtparams.containsKey(fieldName)) {
            return "<";
        }
        if (fielGtparams.containsKey(fieldName)) {
            return ">";
        }
        return "=";
    }

    public String getFieldExpressionParam(String fieldName) {
        if (fielLtparams.containsKey(fieldName)) {
            return "fielLtparams";
        }
        if (fielGtparams.containsKey(fieldName)) {
            return "fielGtparams";
        }
        if (fielEqparams.containsKey(fieldName)) {
            return "fielEqparams";
        }
        return "fielEqparams";
    }

    public String getExpressionParam(String filedName) {
        if (params.containsKey(filedName)) {
            return "params";
        }
        if (paramLikes.containsKey(filedName)) {
            return "paramLikes";
        }
        if (ltParams.containsKey(filedName)) {
            return "ltParams";
        }
        if (gtParams.containsKey(filedName)) {
            return "gtParams";
        }
        return "field";
    }

    public Map<String, Object> getFielEqparams() {
        return fielEqparams;
    }

    public void setFielEqparams(Map<String, Object> fielEqparams) {
        this.fielEqparams = fielEqparams;
    }

    public Map<String, Object> getFielGtparams() {
        return fielGtparams;
    }

    public void setFielGtparams(Map<String, Object> fielGtparams) {
        this.fielGtparams = fielGtparams;
    }

    public Map<String, Object> getFielLtparams() {
        return fielLtparams;
    }

    public void setFielLtparams(Map<String, Object> fielLtparams) {
        this.fielLtparams = fielLtparams;
    }
}
