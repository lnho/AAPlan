package com.lnho.aaplan.param;

import com.lnho.aaplan.commons.page.PageQueryParam;

/**
 * com.lnho.aaplan.param
 *
 * @author lnho
 * @date 14-8-22 下午1:25
 */
public class LogParam extends PageQueryParam {
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
