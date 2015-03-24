package com.lnho.aaplan.commons.web;

import com.lnho.aaplan.commons.utils.UnCaughtException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * web开发工具类
 * 
 * @Company : cyou
 * @author yangz
 * @date 2012-10-10 下午06:05:02
 */
public class WebUtil {
	/**
	 * 判断是否是ajax请求
	 * 
	 * @param request
	 * @return
	 * @author yangz
	 * @date 2012-7-30 下午02:59:10
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return !StringUtils.isEmpty(request.getHeader("X-Requested-With"));
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		String forwardIP = StringUtils.isNotEmpty(request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();

		if ((StringUtils.isNotEmpty(forwardIP)) && (forwardIP.indexOf(",") != -1)) {
			String[] ipRouters = forwardIP.split(",");
			String remoteIP = ipRouters[0];
			return remoteIP;
		}
		return forwardIP;
	}
	
	/**
	 * 获取远程访问的IP地址.
	 * 
	 * @param request
	 * @return
	 * @author yangz
	 * @date 2012-9-18 上午09:02:09
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					throw new UnCaughtException(e);
				}
				ip = inet.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

		return ip;
	}
	
	/**
	 * 将request的Attribute转化为map
	 * @param request
	 * @return
	 * @author yangz
	 * @date 2013-1-23 上午10:09:59
	 */
	public static Map<String, Object> toAttributeMap(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		Enumeration<String> enumeration = request.getAttributeNames();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			result.put(key, request.getAttribute(key));
		}
		return result;
	}
}
