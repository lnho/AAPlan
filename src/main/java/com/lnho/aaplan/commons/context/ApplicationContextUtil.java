package com.lnho.aaplan.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    public static ApplicationContext getContext() {
      return context;
    }

    public static <T> T getBean(String beanId) {
      return (T) context.getBean(beanId);
    }

    public static <T> T getBean(String beanId, Object[] args) {
      return (T) context.getBean(beanId, args);
    }

    public static Object getBeanRmiLoc(String beanRmiId, String beanLocId) {
      return context.getBean(beanRmiId);
    }
  
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
