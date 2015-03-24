package com.lnho.aaplan.commons.mybatis.dao;


import com.lnho.aaplan.commons.mybatis.bean.Query;
import com.lnho.aaplan.commons.mybatis.provider.CrudProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * 用于对象的一些基本操作
 * 使用方法:
 * 1.在Bean上添加相应注解
 * 对象上添加
 * @Table 设置查询的表名
 *
 * 字段上添加
 * @Id 用于设置主键
 * @Column 用于设置字段
 * @SortColumn 用于设置排序的字段,默认为降序
 *
 * 2.继承接口BaseDAO
 *
 * 其他用法:
 * 在子类中可以直接使用
 *  @SelectProvider(type =  CrudProvider.class,method = "findByQuery")
 *  构造select语句,并添加@resultType 就可以完成对象封装
 *  具体查看SysRoleDAO.findRole方法
 *
 *
 * User: wangj
 * Date: 13-11-8
 * Time: 上午9:30
 */
public interface BaseDAO<T > {

    @DeleteProvider(type = CrudProvider.class,method = "delete")
    public void delete(T obj);


    /**
     * 如果是update时候会清掉原有对象的id..慎重使用吧,
     * @param obj
     */
    @InsertProvider(type = CrudProvider.class,method = "save")
    @SelectKey(before=false,keyProperty="id",resultType=Integer.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    public void save(T obj);

    @UpdateProvider(type = CrudProvider.class,method = "update")
    public void update(T obj);

    @InsertProvider(type = CrudProvider.class,method = "insert")
    @SelectKey(before=false,keyProperty="id",resultType=Integer.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    public void insert(T obj);


    /**
     * 构造Query进行查询,返回值可以用ObjectUtil.toBeanList 转成List<T>
     * @param query
     * @return
     */
    @SelectProvider(type =  CrudProvider.class,method = "findByQuery")
    public List<Map<String,Object>> findByQuery(Query<T> query);

    /**
     * 用于统计和分页
     * @param query
     * @return
     */
    @SelectProvider(type =  CrudProvider.class,method = "count")
    public int count(Query<T> query);

    /**
     * 删除操作
     * @param query
     */
    @SelectProvider(type =  CrudProvider.class,method = "deleteByQuery")
    public void deleteByQuery(Query<T> query);

}
