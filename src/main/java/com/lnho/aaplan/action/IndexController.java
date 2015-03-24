package com.lnho.aaplan.action;

/**
 * com.lnho.aaplan.action
 *
 * @author lnho
 * @date 14-7-23 上午10:34
 */

import com.lnho.aaplan.bean.User;
import com.lnho.aaplan.commons.mybatis.util.StringUtil;
import com.lnho.aaplan.commons.page.PageQueryParam;
import com.lnho.aaplan.conf.Global;
import com.lnho.aaplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private Global global;

    @RequestMapping()
    public String list(PageQueryParam pageQueryParam, Model model) {
        List<User> auctionVoList = userService.queryUserListByMoneyDesc(pageQueryParam);
        Double remainMoney = 0.0;
        Double remainDays;
        for (User user : auctionVoList) {
            remainMoney += user.getMoney();
        }
        remainDays = remainMoney / 6 / 12;
        model.addAttribute("data", auctionVoList);
        model.addAttribute("remainMoney", remainMoney);
        model.addAttribute("remainDays", remainDays);
        return "index/list";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Boolean result = userService.checkLogin(request);
        if (result == true) {
            return "redirect:/user.do";
        } else {
            return "index/login";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginCtrl(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isBlank(userName) || StringUtil.isBlank(password)) {
            //登录失败
            return "index/login";
        }
        if (userName.equals(global.getAdminUser()) && password.equals(global.getAdminPass())) {
            //登录成功
            HttpSession session = request.getSession();
            session.setAttribute("isLogin", true);
            return "redirect:/user.htm";
        } else {
            //登录失败
            return "index/login";
        }

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("isLogin", false);
        return "redirect:/index.htm";
    }
}
