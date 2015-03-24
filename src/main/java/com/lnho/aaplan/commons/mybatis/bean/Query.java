package com.lnho.aaplan.commons.mybatis.bean;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 *
 *
 * User: wangj
 * Date: 13-11-15
 * Time: 下午6:20
 */
public class Query<T> {
    public static Map<String,String> MYSQL_SPECIAL_STR = new HashMap<String, String>();

    static {
        MYSQL_SPECIAL_STR.put("/" , "//");
//        MYSQL_SPECIAL_STR.put("0" , "/0");
//        MYSQL_SPECIAL_STR.put("'" , "/'");
//        MYSQL_SPECIAL_STR.put("\"" , "/\"");
//        MYSQL_SPECIAL_STR.put("b" , "/b");
//
//        MYSQL_SPECIAL_STR.put("n" , "/n");
//        MYSQL_SPECIAL_STR.put("r" , "/r");
//        MYSQL_SPECIAL_STR.put("t" , "/t");
//        MYSQL_SPECIAL_STR.put("z" , "/z");

        MYSQL_SPECIAL_STR.put("%" , "/%");
        MYSQL_SPECIAL_STR.put("_" , "/_");
    }


    private Class<T> type;

    Map<String , Between> betweens = new HashMap<String, Between>();

    Map<String , Object> params = new HashMap<String, Object>();

    Map<String , Object> paramLikes = new HashMap<String, Object>();

    Map<String , List<Object>> inArrayParams = new HashMap<String,  List<Object>>();

    Map<String , Object> gtParams = new HashMap<String, Object>();

    Map<String , Object> ltParams = new HashMap<String,  Object>();

    Integer pageNo;

    Integer pageSize;

    List<String> includeField = new ArrayList<String>();

    Map<String,DBOrder> orders = new HashMap<String, DBOrder>();




    public Query(Class<T> type) {
        this.type = type;
    }

    /**
     * 添加in查询
     */
    public Query addIn(String field , List<Object> list) {
        if (list == null  || list.size() == 0) {
            return this;
        }
        this.inArrayParams.put(field , list);
        return this;
    }

    /**
     * 添加in查询
     * @field 是bean里头的属性,不是db中的字段哦 !
     */
    public Query addIn(String field , Object[] objects) {
        if (objects == null  || objects.length == 0) {
            return this;
        }

        List<Object> list = new ArrayList<Object>();
        Collections.addAll(list, objects);
        this.inArrayParams.put(field , list);
        return this;
    }


    /**
     * 添加范围查询
     * @param fieldName
     * @param begin
     * @param end
     */
    public Query addBetween(String fieldName ,Long begin, Long end) {
        if (begin == null  || end == null) {
            return this;
        }

        if (begin > end || begin == 0 || end == 0) {
            return  this;
        }

        betweens.put(fieldName ,new Between( begin ,end)) ;
        return this;
    }

    public Query addBetween(String fieldName ,Object begin, Object end) {
        if (begin == null  || end == null) {
            return this;
        }

        betweens.put(fieldName ,new Between( begin ,end)) ;
        return this;
    }

    /**
     * 添加相等条件的语句
     * @param fieldName
     * @param value
     */
    public Query addEq(String fieldName , Object value) {
        if (value == null) {
            return  this;
        }
        params.put(fieldName , value);
        return this;
    }

    /**
     * 大于
     * @param fieldName
     * @param value
     * @return
     */
    public Query addGt(String fieldName , Object value) {
        if (value == null) {
            return  this;
        }
        gtParams.put(fieldName , value);
        return this;
    }

    /**
     * 小于
     * @param fieldName
     * @param value
     * @return
     */
    public Query addLt(String fieldName , Object value) {
        if (value == null) {
            return  this;
        }
        ltParams.put(fieldName , value);
        return this;
    }


    /**
     * 添加字段的模式匹配,模式匹配的%,_需要自己加
     * @param fieldName
     * @param value
     */
    public Query addLike(String fieldName , String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            String mysqlChar = MYSQL_SPECIAL_STR.get(c+"");
            if (mysqlChar != null) {
                sb.append(mysqlChar);
            } else {
                sb.append(c);
            }
        }


        paramLikes.put(fieldName ,"%" + sb.toString() + "%");
        return this;
    }

    /**
     * 添加字段的前缀匹配
     * @param fieldName
     * @param value
     */
    public Query addPrefixLike(String fieldName , String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            String mysqlChar = MYSQL_SPECIAL_STR.get(c+"");
            if (mysqlChar != null) {
                sb.append(mysqlChar);
            } else {
                sb.append(c);
            }
        }


        paramLikes.put(fieldName , sb.toString() + "%");
        return this;
    }

    /**
     * 添加显示的字段,如果inculdeField为空则查询全部
     * @param fieldName
     */
    public Query addIncludeField(String fieldName) {
        this.includeField.add(fieldName);
        return this;
    }

    /**
     * 设置分页
     * @param pageNo
     * @param pageSize
     */
    public Query setPaged(int pageNo ,int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }

        this.pageNo = pageNo;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 添加排序
     * @param name
     * @param dbOrder
     */
    public Query addOrder(String name , DBOrder dbOrder) {
        this.orders.put(name , dbOrder);
        return this;
    }

    public boolean hasOrder() {
        return this.orders.size() > 0;
    }

    public boolean isLike(String fieldName) {
        return paramLikes.containsKey(fieldName);
    }


    public Map<String, Object> getParams() {
        return params;
    }



    public Class<T> getType() {
        return type;
    }

    public Map<String, Between> getBetweens() {
        return betweens;
    }


    public Map<String, Object> getParamLikes() {
        return paramLikes;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Map<String, List<Object>> getInArrayParams() {
        return inArrayParams;
    }

    public  int getOffset() {
        if (pageNo == null || pageSize == null ) {
            return -1;
        }

        return (pageNo - 1) * pageSize;
    }


    /**
     * 设置要查询的字段
     * @return
     */
    public List<String> getIncludeField() {
        return includeField;
    }

    /**
     * 是否查询所有参数
     * @return
     */
    public boolean isSearchAllField() {
        return includeField.isEmpty();
    }

    /**
     * 字段是否需要放入查询条件
     * @return
     */
    public boolean isFieldSearch(String fieldName) {
        return includeField.contains(fieldName);
    }

    public  static Query build(Class<?> clazz) {
        return new Query(clazz);
    }

    public Map<String, DBOrder> getOrders() {
        return orders;
    }

    public String getExpression(String fieldName) {
        if (params.containsKey(fieldName)) {
            return "=";
        }
        if (paramLikes.containsKey(fieldName)) {
            return " like ";
        }
        if (ltParams.containsKey(fieldName)) {
            return "<";
        }
        if (gtParams.containsKey(fieldName)) {
            return ">";
        }
        return "=";
    }

    public String getExpressionParam(String fieldName) {
        if (params.containsKey(fieldName)) {
            return "params";
        }
        if (paramLikes.containsKey(fieldName)) {
            return "paramLikes";
        }
        if (ltParams.containsKey(fieldName)) {
            return "ltParams";
        }
        if (gtParams.containsKey(fieldName)) {
            return "gtParams";
        }
        return "params";
    }

    public static class Between{
        Object begin;
        Object end;


        public Between( Object begin, Object end) {
            this.begin = begin;
            this.end = end;
        }

        public Object getBegin() {
            return begin;
        }


        public Object getEnd() {
            return end;
        }


    }

    public static enum DBOrder{
        DESC("DESC") ,ASC("ASC") ;

        private DBOrder(String name) {
            this.name = name;
        }

        String name;

        public String getName() {
            return name;
        }
    }

    public static class QueryInParam<E>{
        private List<E> list = new ArrayList<E>();

        public QueryInParam(Collection<? extends E> c) {
            list.addAll(c);
        }

        public QueryInParam(E[] objects){
            Collections.addAll(list, objects);
        }


        public String getArrayStr() {
            StringBuilder sb = new StringBuilder();
            for (Object obj : list) {
                sb.append(obj.toString()).append(",");
            }

            return sb.substring(0 , sb.length() - 1);
        }
    }

    public Map<String, Object> getGtParams() {
        return gtParams;
    }


    public Map<String, Object> getLtParams() {
        return ltParams;
    }

}
