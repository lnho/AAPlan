package com.lnho.aaplan.commons.springmvc;

import org.springframework.core.convert.converter.Converter;

import java.util.regex.Pattern;

/**
 * User: wangj
 * Date: 13-10-29
 * Time: 下午2:37
 */
public class StringConverter  implements Converter<String, String> {
    //过滤<scrpit>内容正则
    private static final String REG_SCRIPT = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>";

    @Override
    public String convert(String text) {
        if(text != null){
            text = text.trim();
            //过滤<scrpit> </script>内容
            Pattern pattern = Pattern.compile(REG_SCRIPT,Pattern.CASE_INSENSITIVE);
            text = pattern.matcher(text).replaceAll("");
            return text;

        }else{
            return "";
        }
    }
}
