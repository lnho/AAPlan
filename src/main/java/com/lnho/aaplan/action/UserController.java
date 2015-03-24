package com.lnho.aaplan.action;

/**
 * com.lnho.aaplan.action
 *
 * @author lnho
 * @date 14-7-23 上午10:34
 */

import com.lnho.aaplan.bean.User;
import com.lnho.aaplan.commons.page.PageQueryParam;
import com.lnho.aaplan.commons.web.Response;
import com.lnho.aaplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping()
    public String list(PageQueryParam pageQueryParam, Model model, HttpServletRequest request) {
        Boolean result = userService.checkLogin(request);
        if (result == false) return "index/login";
        List<User> auctionVoList = userService.list(pageQueryParam);
        model.addAttribute("data", auctionVoList);
        return "user/list";
    }

    @RequestMapping("/charge")
    @ResponseBody
    public Response<Boolean> charge(User user, HttpServletRequest request) {
        Boolean result = userService.checkLogin(request);
        if (result == false) return Response.getFailedResponse();
        Boolean res = userService.charge(user);
        if (res == true) {
            return Response.getSuccessResponse();
        } else {
            return Response.getFailedResponse();
        }
    }

    @RequestMapping("/pay")
    @ResponseBody
    public Response<Boolean> pay(User user, HttpServletRequest request) {
        Boolean result = userService.checkLogin(request);
        if (result == false) return Response.getFailedResponse();
        Boolean res = userService.pay(user);
        if (res == true) {
            return Response.getSuccessResponse();
        } else {
            return Response.getFailedResponse();
        }
    }
}
