package com.lnho.aaplan.dao;

import com.lnho.aaplan.bean.CountResult;
import com.lnho.aaplan.bean.Log;
import com.lnho.aaplan.commons.mybatis.dao.BaseDAO;

import java.util.List;

/**
 * com.lnho.aaplan.dao
 *
 * @author lnho
 * @date 14-7-23 上午10:30
 */
public interface LogDAO extends BaseDAO<Log> {
    public List<CountResult> queryMonthsCount(Integer userId);
}
