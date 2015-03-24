package com.lnho.aaplan.commons.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * User: littlehui
 * Date: 14-5-7
 * Time: 下午5:06
 */

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MultiTable {
    String value() default "";
    String sign() default "";
}
