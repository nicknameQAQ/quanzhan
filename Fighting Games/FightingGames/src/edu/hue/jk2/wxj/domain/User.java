package edu.hue.jk2.wxj.domain;

import java.util.Random;

public class User {
    //属性：id、用户名、密码、状态
    private String id;
    private String username;
    private String password;
    private boolean status;
    /*
    false 表示禁用，true 表示启用
    连续多次登陆失败后账户锁定,但这里逻辑其实不合理,
    别人知道了你的账户就可以一直输入错误密码来让你的账户锁定*/

    //现在需要在javabean类里面写id的方法,其他属性写到登陆注册那里
    /*
    id：
    用户无法设置，是自动生成的，格式为：heima+5位数字的随机数
    */
    public String creatID(){
        StringBuilder sb = new StringBuilder("heima");
        Random r = new Random();
        //ctrl + alt + v自动生成左边
        for (int i1 = 0; i1 < 5; i1++) {
            int i = r.nextInt(10);
            sb.append(i);
        }
        return sb.toString();
    }

    public User() {
        id = creatID();
        status = true;
    }

    public User(String username, String password) {
        id = creatID();
        this.username = username;
        this.password = password;
        status = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
