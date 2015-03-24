package com.lnho.aaplan.bean;

import com.lnho.aaplan.commons.mybatis.annotation.Column;
import com.lnho.aaplan.commons.mybatis.annotation.Id;
import com.lnho.aaplan.commons.mybatis.annotation.Table;
import com.lnho.aaplan.commons.mybatis.annotation.UnColumn;

/**
 * com.lnho.aaplan.bean
 *
 * @author lnho
 * @date 14-7-23 上午10:28
 */
@Table("log")
public class Log {
    @Id("id")
    private Integer id;
    @Column("type")
    private Integer type;  //消费1充值2
    @Column("content")
    private String content;
    @Column("money")
    private Float money;
    @Column("user_id")
    private Integer userId;
    @Column("addtime")
    private Long addtime;
    @Column("left_money")
    private Float leftMoney;
    @UnColumn
    private String userName;
    @UnColumn
    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getAddtime() {
        return addtime;
    }

    public void setAddtime(Long addtime) {
        this.addtime = addtime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Float getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(Float leftMoney) {
        this.leftMoney = leftMoney;
    }

}
