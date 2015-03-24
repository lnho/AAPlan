package com.lnho.aaplan.commons.springmvc;

import com.lnho.aaplan.commons.mybatis.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换
 */
public class DateConverter implements Converter<String, Date> {
    public static final DateFormat DTE_LONG = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DTE_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    /*** 短类型日期长度 */
    public static final int SHORT_DATE_LENGTH = 10;

    @Override
    public Date convert(String source) {
        try {
            if (StringUtil.isEmpty(source)) {
                return null;
            }

            if (StringUtil.isNumeric(source)) {
                return new Date(Long.valueOf(source));
            }

            if (source.length() <= SHORT_DATE_LENGTH) {
                return  (new java.sql.Date(DTE_SHORT.parse(source).getTime()));
            } else {
                return (new java.sql.Timestamp(DTE_LONG.parse(source).getTime()));
            }
        } catch (ParseException ex) {
            RuntimeException iae = new RuntimeException( "Could not parse date: " + ex.getMessage());
            iae.initCause(ex);
            throw iae;
        }
    }
}  