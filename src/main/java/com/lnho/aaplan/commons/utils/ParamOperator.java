/**
 * Title: ParamOperator.java
 * Description: 
 * Copyright: Copyright (c) 2013
 * Company: CYOU
 * @author LittleHui
 * @date 2013-12-9
 * @version 1.0
 */
package com.lnho.aaplan.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Title: ParamOperator
 * Description: 
 * Company: CYOU
 * @author LittleHui
 * @date 2013-12-9
 */
public class ParamOperator {
    
    public static String[] types = {
                       "class java.lang.Short"
                       ,"class java.lang.Integer"
                       ,"class java.lang.Double"
                       ,"class java.util.Date"
                       ,"class java.lang.Short"
                       ,"class java.util.List"
                       ,"class java.lang.Long"
                       ,"class java.lang.String"
                       ,"interface java.util.List"
                       };
    
    /**
     * 用来判断对象是否值为空
     * Title: isEmpty
     * Description: 
     * @param object
     * @return
     * @throws Exception boolean
     * @author LittleHui
     * @date 2013-12-9
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        Class objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();
        boolean isEmpty = true;
        if (fields != null) {
            for (Field field : fields) {
                for (String type : types) {
                    if (field.getType().toString().equals(type)) {
                        // 拿到该属性的gettet方法  
                        /** 
                         * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的 
                         * boolean值不判断，扩展其他对象，请补充types的内容。
                         * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范 
                         */  
                        Method m;
                        try {
                            m = (Method) object.getClass().getMethod(  
                            "get" + getMethodName(field.getName()));
                            Object val = (Object) m.invoke(object);  
                            if (val instanceof List) {
                                if (val != null && ((List<?>)val).size() > 0) {
                                    isEmpty = false;
                                    return isEmpty;
                                }
                            } else {
                                if (val != null) {  
                                    isEmpty = false; 
                                    return isEmpty;
                               }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } 
                    }      
                }
            }
        }
        return isEmpty;
    }

    /**
     * 将param里空格转换成null
     * @param object
     * @param <T>
     * @return
     */
    public static  <T> T transBlankToNull(T object) {
        Class objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getType().toString().equals("class java.lang.String")) {
                    // 拿到该属性的getset方法
                    Method m;
                    Method ms;
                    try {
                        m = (Method) object.getClass().getMethod(
                                "get" + getMethodName(field.getName()));
                        Object val = (Object) m.invoke(object);
                        if (val != null && val.equals("")) {
                            ms = (Method) object.getClass().getMethod(
                                    "set" + getMethodName(field.getName()), String.class);
                            if (ms != null) {
                                ms.invoke(object, new Object[]{null});
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return object;
    }

        /**
         * 获取get名称第一个字符变大写。
         * Title: getMethodName
         * Description:
         * @param fildeName
         * @return
         * @throws Exception String
         * @author LittleHui
         * @date 2013-12-9
         */
    private static String getMethodName(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
     }
}
