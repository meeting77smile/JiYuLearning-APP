package com.example.jiyulearning.entity;

public class UserInfo {
    // 积语——云伴学 的用户对象
    public int id;
    public String account;//账号
    public String username;//用户名
    public String password;//密码
    public int level;//用户水平(1,2,3分别代表新手，中等，高手),默认为新手

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }

    public UserInfo(){};

    public UserInfo(String account, String username, String password, int level) {
        this.account = account;
        this.username = username;
        this.password = password;
        this.level = level;
    }
}
