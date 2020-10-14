package com.ncst.seckill.pojo;

import lombok.Data;

import java.util.Date;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 用户信息类
 */
@Data
public class SeckillUser {
	private Long id;
	private String nickname;
	private String password;
	private String salt;
	private String head;
	private Date registerDate;
	private Date lastLoginDate;
	private Integer loginCount;

}
