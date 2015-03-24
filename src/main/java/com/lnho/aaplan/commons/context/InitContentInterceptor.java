package com.lnho.aaplan.commons.context;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * web容器拦截器, 获取Request, Session, response
 *
 * @author yangz
 * @Company : cyou
 * @date 2012-10-8 下午03:49:24
 * @linked InitFilter.java
 */
public class InitContentInterceptor implements Filter {

    Logger logger = Logger.getLogger(InitContentInterceptor.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        WebContext.setRequest(httpRequest);
        WebContext.setResponse((HttpServletResponse) response);
        WebContext.getResponse().setContentType("text/html;charset=UTF-8");
        String url = httpRequest.getServletPath();
        try {

            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            WebContext.remove();
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
