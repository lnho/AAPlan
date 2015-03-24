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
@Table("user")
public class User {
    @Id("id")
    private Integer id;
    @Column("username")
    private String username;
    @Column("money")
    private Float money;
    @UnColumn
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
