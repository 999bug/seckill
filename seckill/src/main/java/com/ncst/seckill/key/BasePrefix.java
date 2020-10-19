package com.ncst.seckill.key;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public class BasePrefix implements KeyPrefix{
	
	private int expireSeconds;
	
	private String prefix;
	
	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}
	
	public BasePrefix( int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	@Override
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}

	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
