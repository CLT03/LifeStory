package com.vivwe.base.entity;

/**
 * ahtor: super_link
 * date: 2019/5/18 09:50
 * remark:
 */
public class UserToken {
    /** 用户ID */
    private int id;
    /** 用户账号 */
    private String account;
    /** 用户密码 */
    private String password;
    /** TOKEN */
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
