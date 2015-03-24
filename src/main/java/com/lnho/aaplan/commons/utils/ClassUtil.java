package com.lnho.aaplan.commons.utils;

import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @Description: Class工具类
 * @Company : cyou
 * @author yangz
 * @date 2012-9-26 下午02:55:57
 * @version V1.0
 */
public class ClassUtil {
	
	private static final Logger log = Logger.getLogger(ClassUtil.class) ;
	/**
	 * 是否是值类型
	 * 
	 * @param clz
	 * @return
	 * @author yangz
	 * @date 2012-9-26 下午03:17:09
	 */
	public static boolean isValueType(Class<?> clz) {
		try {
			if (clz != null) {
				if (clz.isPrimitive() || Number.class.isAssignableFrom(clz)
						|| Character.class.isAssignableFrom(clz)
						|| String.class.isAssignableFrom(clz)
						|| Date.class.isAssignableFrom(clz)
						|| Boolean.class.isAssignableFrom(clz)) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new RuntimeException("Class can't be null");
			}
		} catch (Exception e) {
			log.error("在判断是否是值类型的时候出现了异常：" , e) ;
			return false;
		}
	}

	/**
	 * 实例化
	 * 
	 * @param className
	 * @return
	 * @author yangz
	 * @date 2012-7-29 上午10:55:03
	 */
	public static Object newInstance(String className) {
		Object result = null;
		try {
			result = newInstance(Class.forName(className));
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException();
			ex.initCause(e);
			throw ex;
		}
		return result;
	}

	/**
	 * 实例化
	 * 
	 * @param c
	 * @return
	 * @author yangz
	 * @date 2012-7-29 上午10:57:08
	 */
	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class c) {
		Object result = null;
		try {
			result = c.newInstance();
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException();
			ex.initCause(e);
			throw ex;
		}
		return result;
	}

	/**
	 * 获取字段, 找不到字段抛异常
	 * 
	 * @param clz
	 * @param fieldName
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午04:08:47
	 */
	@SuppressWarnings("rawtypes")
	public static Field getField(Class clz, String fieldName) {
		return getField(clz, fieldName, true);
	}
	
	/**
	 * 缓存Field
	 */
	private final static Map<String,Field> fieldCache =  new ConcurrentHashMap<String,Field>();
	/**
	 * 获取字段, 找不到字段可返回null  exception=false时
	 * 
	 * @param clz
	 * @param fieldName
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午04:08:47
	 */
	@SuppressWarnings("rawtypes")
	public static Field getField(Class clz, String fieldName, boolean exception) {
		String key = clz.getName()+" - " +fieldName;
		Field f = fieldCache.get(key);
		if (f != null) {
			return f;
		}
		
		for (; clz != Object.class ; clz = clz.getSuperclass()){
			try {
				if (!Object.class.getName().equals(clz.getName())) {
					Field field = clz.getDeclaredField(fieldName);
					fieldCache.put(key, field);
					return field;
				} 
			} catch (NoSuchFieldException e) {
				//DO NOTHING
			}
		}
		
		if(exception){
			throw new RuntimeException("no such field in " + clz.getName());
		}
		return null;
	}
	
	/**
	 * 取泛型类型
	 * 
	 * @param type
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午04:28:40
	 */
	@SuppressWarnings("rawtypes")
	public static Type[] getActualTypes(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType t = (ParameterizedType) type;
			Type[] types = t.getActualTypeArguments();
			if (ObjectUtil.isEmpty(types)) {
				throw new RuntimeException(((Class) type).getName() + " not have ActualType.");
			}
			return types;
		}else if(type instanceof Class){
			Type[] types = null;
			if(isCGLibProxy((Class) type)){//是否是CGLib代理对象
				Class proxyType = (Class) ((Class) type).getGenericSuperclass(); //代理类
				types = ((ParameterizedType) proxyType.getGenericSuperclass()).getActualTypeArguments();
			}else{
				types = ((ParameterizedType) ((Class) type).getGenericSuperclass()).getActualTypeArguments();
			}
			if (ObjectUtil.isEmpty(types)) {
				throw new RuntimeException(((Class) type).getName() + " not have ActualType.");
			}
			return types;
		} else {
			throw new RuntimeException("type error.");
		}
	}
	/**
	 * 是否是cglib代理类
	 * @param type
	 * @return
	 * @author yangz
	 * @date 2013-1-5 下午02:10:31
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isCGLibProxy(Class type){
		//FIXME 这种方法可行, 但是比较土
    	return type.getName().contains("EnhancerByCGLIB");
    }

	/**
	 * 取泛型类型
	 * 
	 * @param type
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午04:28:40
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getActualType(Type type) {
		return (Class<T>) getActualTypes(type)[0];
	}
	/**
	 * 泛型对象中是否有指定属性
	 * @param clz
	 * @param propertyName
	 * @return
	 * @author yangz
	 * @date 2012-9-29 上午11:21:59
	 */
	@SuppressWarnings("rawtypes")
	public static boolean actualTypeHasProperty(Class clz, String propertyName){
		try {
			Field field = getField(getActualType(clz), propertyName ,false);
			if (field != null) {
				return true;
			}
		} catch (Exception e) {
			log.error("在泛型对象中是否有指定属性的方法中出现了异常：", e) ;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 是否有指定属性
	 * @param clz
	 * @param propertyName
	 * @return
	 * @author yangz
	 * @date 2012-9-29 上午11:21:59
	 */
	@SuppressWarnings("rawtypes")
	public static boolean hasProperty(Class clz, String propertyName){
		try {
			Field f = getField(clz, propertyName,false);
			if (f != null) {
				return true;
			}
		} catch (Exception e) {
			log.error("在是否有指定属性的方法中出现了异常：" , e) ;
			
		}
		return false;
	}

    public static boolean isProperty(Class clz , String property) {
        try {
            PropertyDescriptor descriptor = getProperty(Introspector.getBeanInfo(clz) , property);
            if (descriptor != null) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	/**
	 * 获取属性类型
	 * @param beanInfo
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-11 下午06:32:14
	 */
	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(BeanInfo beanInfo, String property){
		return getProperty(beanInfo, property).getPropertyType();
	}
	/**
	 * 获取属性字段
	 * @param beanInfo
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-12 下午04:50:08
	 */
	public static PropertyDescriptor getProperty(BeanInfo beanInfo, String property){
		PropertyDescriptor[] propertys = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertys) {
			if(propertyDescriptor.getName().equals(property) && !"class".equals(property)){
				return propertyDescriptor;
			}
		}
		return null;
	}
	/**
	 * 获取属性字段
	 * @param beanClass
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-12 下午04:52:20
	 */
	public static PropertyDescriptor getProperty(Class<?> beanClass, String property){
		try {
			return getProperty(Introspector.getBeanInfo(beanClass), property);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 读取属性值
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-12 下午04:55:14
	 */
	public static Object readProperty(Object obj, String property){
		try {
			return getProperty(obj.getClass(), property).getReadMethod().invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
