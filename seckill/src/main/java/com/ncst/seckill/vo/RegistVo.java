package com.ncst.seckill.vo;

/**
 * @Date 2020/10/19 16:30
 * @Author by LSY
 * @Description
 */
public class RegistVo {
    private long id;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistVo{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
