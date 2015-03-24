package com.lnho.aaplan.commons.context;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListenerManager extends ContextLoaderListener implements ServletContextListener {
    private ServletContext context;
    private WebApplicationContext webApplicationContext;
    private final static String APPLICATIONCONTEXTUYIL = "applicationContextUtil";

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        this.context = event.getServletContext();
        this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        this.context.setAttribute("WEBAPPLICATIONCONTEXT", webApplicationContext);
        WebApplicationContext webApplicationContext = (WebApplicationContext) this.context.getAttribute("WEBAPPLICATIONCONTEXT");
        ApplicationContextUtil aplicationContextUtil = (ApplicationContextUtil) webApplicationContext.getBean(APPLICATIONCONTEXTUYIL);
    }
}
