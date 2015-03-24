package com.lnho.aaplan.commons.web;

import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局异常处理
 * @Company : cyou
 * @author yangz
 * @date   2012-10-10 上午09:11:47
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	public final static String ERROR_PAGE = "/exception/error.htm";
	public final static String NOTFOUND_PAGE = "/exception/404.htm";
	/**
	 * 处理全局异常
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 * @author yangz
	 * @date 2012-10-10 上午09:40:02
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			String url = request.getRequestURI();
            LogFactory.getLog(handler.getClass()).error(url + "|异常:" + ex, ex);

			if(WebUtil.isAjaxRequest(request)){
                response.setContentType("application/json");
				Response<Boolean> global = new Response<Boolean>();
				if(ex instanceof RuntimeException){
					//校验错误返回input错误代码
					global.setResult(Response.RESULT_INPUT);
				}else{
					//其他逻辑错误返回failture错误代码
					global.setResult(Response.RESULT_FAILURE);
				}
				global.setData(false);
				global.setMessage("The request could not be completed.");
				Jackson2Util.writeJson(request ,response, global);
				return new ModelAndView();
			}else{
				return new ModelAndView(ERROR_PAGE);
			}
		} catch (Exception e) {
            e.printStackTrace();
			LogFactory.getLog(GlobalExceptionHandler.class).error(e);
			LogFactory.getLog(handler.getClass()).error(ex);
			return new ModelAndView(ERROR_PAGE);
		}
	}
}
