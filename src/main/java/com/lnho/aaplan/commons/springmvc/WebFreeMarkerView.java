package com.lnho.aaplan.commons.springmvc;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebFreeMarkerView extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String ctx = request.getContextPath();
        ctx += "/";
        model.put("ctx", ctx);
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin != null && (Boolean) isLogin == true) {
            isLogin = true;
        } else {
            isLogin = false;
        }
        model.put("isLogin",isLogin);
    }

}
