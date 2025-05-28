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

    public UserInfo(String userInfo) {
        // 移除前缀"UserInfo{"和后缀"}"
        String content = userInfo.substring("UserInfo{".length(), userInfo.length() - 1);

        // 分割各个字段
        String[] fields = content.split(", ");

        // 逐个解析字段
        for (String field : fields) {
            String[] keyValue = field.split("=", 2);
            if (keyValue.length != 2) continue;

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            try {
                switch (key) {
                    case "id":
                        this.id = Integer.parseInt(value);
                        break;
                    case "account":
                        // 移除单引号
                        this.account = value.substring(1, value.length() - 1);
                        break;
                    case "username":
                        // 移除单引号
                        this.username = value.substring(1, value.length() - 1);
                        break;
                    case "password":
                        // 移除单引号
                        this.password = value.substring(1, value.length() - 1);
                        break;
                    case "level":
                        this.level = Integer.parseInt(value);
                        break;
                }
            } catch (NumberFormatException e) {
                // 处理数字转换异常
                e.printStackTrace();
            }
        }
    }
}
