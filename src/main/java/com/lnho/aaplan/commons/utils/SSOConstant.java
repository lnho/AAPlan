package com.lnho.aaplan.commons.utils;

/**
 * 单点登录常量.
 * 
 * @author qingwu
 * @date 2014-1-17 下午5:44:43
 */
public class SSOConstant {

	/**
	 * 用户名与密码不符合.
	 */
	public static String AUTH_ERROR = "AUTH_ERROR";
	
	/**
	 * 用户名与密码不符合.
	 */
	public static String AUTH_ERROR_5 = "AUTH_ERROR_5";
	
	/**
	 * 用户不存在.
	 */
	public static String USER_NOT_EXIST = "USER_NOT_EXIST";
	
	/**
	 * 系统错误.
	 */
	public static String SYS_ERROR = "SYS_ERROR";

	/**
	 * 请求参数错误.
	 */
	public static String PARAM_ERROR = "PARAM_ERROR";

	/**
	 * cookie_ticket的名称.
	 */
	public static String AUTH_URL;

	/**
	 * @return the aUTH_ERROR
	 */
	public String getAUTH_ERROR() {
		return AUTH_ERROR;
	}

	/**
	 * @param aUTH_ERROR
	 *            the aUTH_ERROR to set
	 */
	public void setAUTH_ERROR(String aUTH_ERROR) {
		AUTH_ERROR = aUTH_ERROR;
	}

	/**
	 * @return the pARAM_ERROR
	 */
	public String getPARAM_ERROR() {
		return PARAM_ERROR;
	}

	/**
	 * @param pARAM_ERROR
	 *            the pARAM_ERROR to set
	 */
	public void setPARAM_ERROR(String pARAM_ERROR) {
		PARAM_ERROR = pARAM_ERROR;
	}

	/**
	 * @return the authUrl
	 */
	public String getAuthUrl() {
		return AUTH_URL;
	}

}
