package com.ncst.seckill.key.prefix;

import com.ncst.seckill.key.BasePrefix;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public class UserKey extends BasePrefix {

	private UserKey(String prefix) {
		super(prefix);
	}
	public static UserKey getById = new UserKey("id");
	public static UserKey getByName = new UserKey("name");
}
