package com.lnho.aaplan.service;

import com.lnho.aaplan.bean.CountResult;
import com.lnho.aaplan.bean.Log;
import com.lnho.aaplan.commons.mybatis.bean.Query;
import com.lnho.aaplan.commons.mybatis.service.BaseService;
import com.lnho.aaplan.dao.LogDAO;
import com.lnho.aaplan.param.LogParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.lnho.aaplan.service
 *
 * @author lnho
 * @date 14-7-23 上午10:29
 */
@Service
public class LogService extends BaseService<Log> {
    @Autowired
    private UserService userService;
    @Autowired
    private LogDAO logDAO;

    public List<Log> list(LogParam pageQueryParam) {
        Query query = Query.build(Log.class);
        query.setPaged(pageQueryParam.getPageNo(), pageQueryParam.getPageSize());
        Integer user_id = pageQueryParam.getUserId();
        if (user_id != null && user_id != 0) {
            query.addEq("userId", pageQueryParam.getUserId());
        }
        query.addOrder("addtime", Query.DBOrder.DESC);
        pageQueryParam.setTotalHit(this.count(query));
        List<Log> logs = this.findByQuery(query);
        for (Log log : logs) {
            log.setUserName(userService.get(log.getUserId()).getUsername());
        }
        return logs;
    }
    public List<CountResult> list(Integer userId) {
        List<CountResult> list=logDAO.queryMonthsCount(userId);
        return list;
    }
}
