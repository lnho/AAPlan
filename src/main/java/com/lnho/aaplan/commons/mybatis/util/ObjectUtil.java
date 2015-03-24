package com.lnho.aaplan.commons.mybatis.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * @Description: 对象工具类
 * @Company : cyou
 * @author yangz
 * @date   2012-9-26 下午02:53:41
 * @version V1.0
 */
public class ObjectUtil {
	
	private static final Log log = LogFactory.getLog(ObjectUtil.class);
	
	/**
	 * One of the following conditions isEmpty = true, else = false :
	 * 满足下列一个条件则为空<br>
	 * 1. null : 空<br>
	 * 2. "" or " " : 空串<br>
	 * 3. no item in [] or all item in [] are null : 数组中没有元素, 数组中所有元素为空<br>
	 * 4. no item in (Collection, Map, Dictionary) : 集合中没有元素<br>
	 * 
	 * @param value
	 * @return
	 * @author yangz
	 * @date May 6, 2010 4:21:56 PM
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)
				&& ((((String) value).trim().length() <= 0) || "null".equalsIgnoreCase((String) value))) {
			return true;
		}
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
			return true;
		}
		if (value instanceof Object[]) { // all item in [] are null :
			// 数组中所有元素为空
			Object[] t = (Object[]) value;
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					if(t[i] instanceof String){
						if(((String) t[i]).trim().length() > 0 || "null".equalsIgnoreCase((String) t[i])){
							return false;
						}
					}else{
						return false;
					}
				}
			}
			return true;
		}
		if ((value instanceof Collection) && ((Collection<?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Dictionary) && ((Dictionary<?,?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Map) && ((Map<?,?>) value).size() <= 0) {
			return true;
		}
		return false;
	}

    /**
     * list<String>判空
     * @author wangj
     * @param list
     * @return
     */
    public static boolean isEmpty(List<String> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        List<String> newStrs = new ArrayList<String>();
        for (String str : list) {
            if (!StringUtils.isEmpty(str)) {
                newStrs.add(str);
            }
        }
        if (newStrs.size() > 0) {
            return false;
        }
        return true;
    }

	/***
	 * Bean的copy方法，使用Spring-BeanUtils.copyProperties方法
	 * 若出现转换异常则抛出CommonRuntimeException
	 * @author jianchen
	 * 2013-6-13
	 */
	public static Object copyPorperties(Object source,Object target){
		try{
			BeanUtils.copyProperties(source, target);
		}catch(BeansException e){
			log.debug("ObjectUtil copyProperties bad for src :"+ source.toString()+" dest: "+target.toString());
			throw new RuntimeException("ObjectUtil copyProperties bad for src :"+ source.toString()+" dest: "+target.toString());
		}
		return target;
	}
	
	/**
	 * 对象那个转换啊 例如:Game -> simpleGame
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <M,T> T convertObj(M obj , Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			ObjectUtil.copyPorperties(obj, instance);
			return instance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("convert obj error! source class :"+obj.getClass()+",target :"+clazz);
	}
	
	public static <M,T> List<T> convertList(List<M> objList , Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (M m : objList) {
			list.add(convertObj(m, clazz));
		}
		return list;
	}

    public static <M,T> List<T> convertList(List<M> objList ,Converter<M,T> converter) {
        List<T> list = new ArrayList<T>();
        for (M m : objList) {
            list.add(converter.convert(m));
        }
        return list;
    }

    public static interface  Converter<H,Q>{
        public Q convert(H h);
    }
	
	
	/**
	 * 对象是否是值类型
	 * @param obj
	 * @return
	 * @author yangz
	 * @date 2012-9-26 下午03:01:44
	 */
	public static boolean isValueType(Object obj){
		if (obj instanceof String || obj instanceof Number || obj instanceof Boolean || obj instanceof Character || obj instanceof Date) {
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 是否是集合
	 * @param obj
	 * @return
	 * @author yangz
	 * @date 2012-9-26 下午03:50:55
	 */
	public static boolean isCollection(Object obj){
		if (obj instanceof Collection<?>) {
			return true;
		}else{
			return false;
		}
	}

    /**
     * 转换成实体
     * @param clazz
     * @param mapList
     * @param <M>
     * @return
     */
    public static <M> List<M> toBeanList(Class<M> clazz , List<Map<String,Object>> mapList) {
        List<M> objectList = new ArrayList<M>();
        for (Map<String,Object> map : mapList) {
            objectList.add( toBean(clazz , map) );
        }
        return objectList;
    }

	/**
	 * 将map值转化成对象, 无递归嵌套
	 * @param type
	 * @param map
	 * @return
	 * @author yangz
	 * @date 2012-9-26 下午03:39:54
	 */
    public static  <M> M toBean(Class<M> type, Map<String, Object> map) {
        M obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
			obj = type.newInstance();
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
			    PropertyDescriptor descriptor = propertyDescriptors[i];
			    String propertyName = descriptor.getName();
			    if (map.containsKey(propertyName)) {
			        Object value = map.get(propertyName);
			        Object[] args = new Object[1];
			        args[0] = value;
			        descriptor.getWriteMethod().invoke(obj, args);
			    }
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent map to object error");
			ex.initCause(e);
			throw ex;
		} 
        return obj;
    }
    /**
     * 将map值转化成对象, 无递归嵌套
     * @param type
     * @param map
     * @return
     * @author yangz
     * @date 2012-9-26 下午03:39:54
     */
    public static  <M> M addToBean(M obj ,Class<M> type, Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (Exception e) {
            RuntimeException ex = new RuntimeException("convent map to object error");
            ex.initCause(e);
            throw ex;
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean
     * @return
     * @author yangz
     * @date 2012-9-26 下午03:40:56
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> toMap(Object bean){
        Map<String, Object> returnMap;
		try {
			Class<?> type = bean.getClass();
			returnMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
			    PropertyDescriptor descriptor = propertyDescriptors[i];
			    String propertyName = descriptor.getName();
			    if (!propertyName.equals("class")) {
			        Method readMethod = descriptor.getReadMethod();
			        Object result = readMethod.invoke(bean, new Object[0]);
			        if(ObjectUtil.isValueType(result) || result == null){
			        	returnMap.put(propertyName, result);
			        }else if(ObjectUtil.isCollection(result)){
			        	Collection<?> collectionResult = (Collection<?>)result;
						Collection collection = (Collection) result.getClass().newInstance();
			        	for (Object o : collectionResult) {
			        		 if(ObjectUtil.isValueType(o) || o == null){
			        			 collection.add(o);
			        		 }else{
			        			 collection.add(toMap(o));
			        		 }
						}
			        	returnMap.put(propertyName, collection);
			        }else if(result.getClass().isArray()){
			        	throw new RuntimeException("bean property can't be array");
			        }else{ //自定义对象
			        	returnMap.put(propertyName, toMap(result));
			        }
			    }
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent object to map error");
			ex.initCause(e);
			throw ex;
		} 
        return returnMap;
    }
}
