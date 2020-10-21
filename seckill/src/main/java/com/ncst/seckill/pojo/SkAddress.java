package com.ncst.seckill.pojo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author LSY
 */
@Getter
@Setter
public class SkAddress {

  /**
   * 收件地址 id  主键
   */

  private long aid;
  /**
   * 用户id
   */
  private long uid;
  private String nickname;
  private String address;
  private long phone;
  private Date createdTime;
  private Date modifiedTime;

  @Override
  public String toString() {
    return "SkAddress{" +
            "aid=" + aid +
            ", uid=" + uid +
            ", nickname='" + nickname + '\'' +
            ", address='" + address + '\'' +
            ", phone=" + phone +
            ", createdTime=" + createdTime +
            ", modifiedTime=" + modifiedTime +
            '}';
  }
}
