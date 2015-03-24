package com.lnho.aaplan.commons.mybatis.dao;

import com.lnho.aaplan.commons.mybatis.bean.MultiQuery;
import com.lnho.aaplan.commons.mybatis.provider.MultiCrudProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * User: littlehui
 * Date: 14-5-7
 * Time: 下午4:26
 */
public interface MultiQueryBaseDAO<T> extends BaseDAO<T> {

    /**
     * 用于支持MultiQuery。不能删掉哦
     * @param multiQuery
     * @return
     */
    @SelectProvider(type =  MultiCrudProvider.class,method = "multiCount")
    public int multiCount(MultiQuery<T> multiQuery);

}
