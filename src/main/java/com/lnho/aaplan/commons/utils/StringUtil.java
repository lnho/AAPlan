package com.lnho.aaplan.commons.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * 
 * @author yangz
 * @date   2012-7-28 下午02:43:17 
 */
public class StringUtil extends StringUtils{
	
	public static String WORD_SEPARATE = "-";
	/**
	 * 字符串是否存在中文
	 * @param str
	 * @return
	 * @author yangz
	 * @date 2012-9-21 下午03:24:33
	 */
	public static boolean isExistZH(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}

    public static boolean isAllZH(String str) {
        String regEx = "^[\\u4e00-\\u9fa5]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

	/**
	 * 字符串第一个字母大写
	 * @param s
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午03:10:46
	 */
	public static String upperFirstChar(String s) {
		if (!isEmpty(s)) {
			return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
		} else {
			return s;
		}
	}
	/**
	 * 字符串第一个字母小写
	 * @param s
	 * @return
	 * @author yangz
	 * @date 2012-9-27 下午03:10:58
	 */
	public static String lowerFirstChar(String s) {
		if (!isEmpty(s)) {
			return String.valueOf(s.charAt(0)).toLowerCase() + s.substring(1);
		} else {
			return s;
		}
	}
	/**
	 * 获取第一个大写字母
	 * @param s
	 * @return
	 * @author yangz
	 * @date 2012-10-23 上午09:06:18
	 */
	public static String getUpperFirstChar(String s) {
		if (!isEmpty(s)) {
			return String.valueOf(s.charAt(0)).toUpperCase();
		} else {
			return s;
		}
	}
	
	/**
	 * 四舍五入并去掉科学计数法, 默认小数点2位
	 * @param value String, double, Double, BigDecimal
	 * @return
	 * @author yangz
	 * @date 2012-7-28 下午03:44:05
	 */
	public static String toNuSicen(Object value) {
		return toNuSicen(value, 2);
	}
	/**
	 * 四舍五入并去掉科学计数法
	 * @param value String, double, Double, BigDecimal
	 * @param precision 保留几位小数
	 * @return
	 * @author yangz
	 * @date 2012-7-28 下午03:47:25
	 */
	public static String toNuSicen(Object value, int precision) {
		Object result = "";
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(precision);
		df.setMaximumFractionDigits(precision);
		df.setGroupingUsed(false);
		if(value instanceof BigDecimal){
			result = value;
		}else if(value instanceof String){
			result = new BigDecimal(String.valueOf(value));
		}else if(value instanceof Number){
			result = ValueUtil.getDouble(value);
		}else{
			throw new RuntimeException(value + "need extends Number or String");
		}
		return df.format(result);
	}
	/**
	 * 获取不区分大小写正则Pattern
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-10-9 下午03:32:33
	 */
	public static Pattern getInsensitivePattern(String value){
		return Pattern.compile(ValueUtil.getString(value).replaceAll("([\\+\\-\\&\\.\\|\\!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:])","\\\\$1"), Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
//		if(v.matches("[\\u4e00-\\u9fa5|\\w\\s]*")){
//			return Pattern.compile(v, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
//		}else{
//			return Pattern.compile(UUID.generate(), Pattern.CASE_INSENSITIVE + Pattern.DOTALL); //忽略特殊字符查询
//		}
	}
	

	
	/**
	 * 获取指定位数的随机数窜
	 * @author jianchen
	 * 2013-4-8
	 */
	public static String getRandomAsString(int max,int min) {
		return String.valueOf(Math.round(Math.random()*(max-min)+min));
	}
	
	/**
	 * 判断字符串数组中是否存在为null or “” 情况
	 * @param strs
	 * @return
	 */
	public static boolean hasEmptyStr(String... strs) {
		for (String str : strs) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

    public static boolean allEmptyStr(String... strs) {
        for (String str : strs) {
            if (!isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组转字符串并用','分隔
     * @param strs
     * @return
     */
    public static String arrayToString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str).append(",");
        }
        return sb.toString().substring(0 , sb.toString().length() -1);
    }
}
