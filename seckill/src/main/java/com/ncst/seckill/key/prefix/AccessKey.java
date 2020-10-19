package com.ncst.seckill.key.prefix;

import com.ncst.seckill.key.BasePrefix;

/**
 * @author LSY
 */
public class AccessKey extends BasePrefix {

	private AccessKey( int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
	
}