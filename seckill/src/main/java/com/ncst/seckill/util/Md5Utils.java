package com.ncst.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public class Md5Utils {
	
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static final String SALT = "1a2b3c4d";


	public static String inputPassToFormPass(String inputPass) {
		String str = ""+SALT.charAt(0)+SALT.charAt(2) + inputPass +SALT.charAt(5) + SALT.charAt(4);
		return md5(str);
	}
	
	public static String fromInputPwdToDbPwd(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPassToDbPass(String inputPass, String saltDb) {
		String formPass = inputPassToFormPass(inputPass);
		return fromInputPwdToDbPwd(formPass, saltDb);
	}

}
