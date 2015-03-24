package com.lnho.aaplan.commons.context;

import com.lnho.aaplan.commons.web.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: wangj
 * Date: 13-11-12
 * Time: 上午10:46
 */
public class WebContext implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(WebContext.class);
    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
    private static Map<String ,String> cacheKey = new HashMap<String, String>();

    /**
     * 注入使用的容器
     */
    private static ApplicationContext applicationContext;

    /**
     * 放入web请求
     *
     * @param r
     * @author yangz
     * @date 2012-10-8 下午04:22:36
     */
    public static void setRequest(HttpServletRequest r) {
        request.set(r);
    }

    /**
     * 获取当前request
     *
     * @return
     * @author yangz
     * @date 2012-10-18 上午10:50:32
     */
    public static HttpServletRequest getRequest() {
        return request.get();
    }

    /**
     * 得到当前线程response
     *
     * @return
     * @author yangz
     * @date 2012-10-13 下午06:00:37
     */
    public static HttpServletResponse getResponse() {
        if (null == response.get()) {
            return null;
        }
        return response.get();
    }

    /**
     * 放入当前线程response
     *
     * @param r
     * @author yangz
     * @date 2012-10-13 下午06:00:40
     */
    public static void setResponse(HttpServletResponse r) {
        response.set(r);
    }

    /**
     * 删除web请求
     *
     * @author yangz
     * @date 2012-10-8 下午04:22:49
     */
    public static void remove() {
        request.remove();
        response.remove();
    }

    public static <T> T getObjectFromSrping(Class<T> clazz) {
        T object = applicationContext.getBean(clazz);
        if (object != null) {
            return object;
        }
        throw new RuntimeException("no config ");
    }



    public static String getUserIpAddr() {
        if (request.get() == null) {
            return "";
        }
        return WebUtil.getRemoteIP(request.get());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebContext.applicationContext = applicationContext;
    }

}
