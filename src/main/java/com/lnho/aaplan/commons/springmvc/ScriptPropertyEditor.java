package com.lnho.aaplan.commons.springmvc;


import java.beans.PropertyEditorSupport;

/**
 * 跳过过滤scipt标签
 * Created by Administrator on 14-1-17.
 */
public class ScriptPropertyEditor extends PropertyEditorSupport {
    public void setAsText(String text) throws IllegalArgumentException {
        if(text != null){
            setValue(text);
        }else{
            setValue("");
        }
    }

    public String getAsText() {
        Object value = getValue();
        return value == null ? "" : value.toString().trim();
    }
}
