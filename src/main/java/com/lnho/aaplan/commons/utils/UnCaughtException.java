package com.lnho.aaplan.commons.utils;

/**
 * 不被捕获的异常,将抛至最顶层.
 * 
 * @author yangz
 * @date 2012-7-28 下午03:07:44
 */
@SuppressWarnings("serial")
public class UnCaughtException extends RuntimeException {

	public UnCaughtException() {
		super();
	}

	public UnCaughtException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnCaughtException(String message) {
		super(message);
	}

	public UnCaughtException(Throwable cause) {
		super(cause);
	}

}
