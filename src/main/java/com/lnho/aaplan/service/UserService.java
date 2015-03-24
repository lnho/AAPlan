package com.lnho.aaplan.service;

import com.lnho.aaplan.bean.Log;
import com.lnho.aaplan.bean.User;
import com.lnho.aaplan.commons.mybatis.bean.Query;
import com.lnho.aaplan.commons.mybatis.service.BaseService;
import com.lnho.aaplan.commons.page.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * com.lnho.aaplan.service
 *
 * @author lnho
 * @date 14-7-23 上午10:29
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    private LogService logService;

    public List<User> list(PageQueryParam pageQueryParam) {
        Query query = Query.build(User.class);
        query.setPaged(pageQueryParam.getPageNo(), pageQueryParam.getPageSize());
        pageQueryParam.setTotalHit(this.count(query));
        List<User> users = findByQuery(query);
        return users;
    }
    public List<User> queryUserListByMoneyDesc(PageQueryParam pageQueryParam) {
        Query query = Query.build(User.class);
        query.setPaged(pageQueryParam.getPageNo(), pageQueryParam.getPageSize());
        query.addOrder("money", Query.DBOrder.DESC);
        pageQueryParam.setTotalHit(this.count(query));
        List<User> users = findByQuery(query);
        return users;
    }

    public Boolean pay(User user) {
        User userOrig = get(user.getId());
        Float moneyOrig = userOrig.getMoney();
        userOrig.setMoney(moneyOrig - user.getMoney());
        update(userOrig);
        Log log = new Log();
        log.setType(1);
        log.setUserId(user.getId());
        log.setContent(user.getContent());
        log.setMoney(user.getMoney());
        log.setAddtime(System.currentTimeMillis());
        log.setLeftMoney(userOrig.getMoney());
        logService.insert(log);
        return true;
    }

    public Boolean charge(User user) {
        User userOrig = get(user.getId());
        Float moneyOrig = userOrig.getMoney();
        userOrig.setMoney(moneyOrig + user.getMoney());
        update(userOrig);
        Log log = new Log();
        log.setType(2);
        log.setUserId(user.getId());
        log.setContent(user.getContent());
        log.setMoney(user.getMoney());
        log.setAddtime(System.currentTimeMillis());
        log.setLeftMoney(userOrig.getMoney());
        logService.insert(log);
        return true;
    }
    public Boolean checkLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin != null && (Boolean) isLogin == true) {
            return true;
        }
        return false;
    }
}
