package com.vivwe.main.entity;

import java.math.BigDecimal;

/**
 * ahtor: super_link
 * date: 2019/5/14 10:07
 * remark: 用户登录信息
 */
public class UserLoginInfoEntity {
    /** id */
    private int id;
    /** 用户账号 */
    private String account;
    /** 地址 */
    private String address;
    /** 生日 */
    private String birthday;
    /** 教育背景 */
    private String eduBackground;
    /** email地址 */
    private String email;
    /** 名字 */
    private String firstName;
    /** 姓 */
    private String lastName;
    /** 性别0：男， 1：女 */
    private int gender;
    /** 收入 */
    private BigDecimal income;
    /** 行业 */
    private String industry;
    /** 昵称 */
    private String nickname;
    /** 密码 */
    private String password;
    /** 手机号 */
    private String phoneNumber;
    /** 注册时间 */
    private String registrationTime;
    /**  */
    private String salt;
    /** 登录token */
    private String token;
    /** 化身数据 */
    private String avatar;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
