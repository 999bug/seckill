package com.ncst.seckill.exception;

import com.ncst.seckill.result.CodeMsg;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 全局异常处理
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private CodeMsg cm;
	
	public GlobalException(CodeMsg cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public CodeMsg getCm() {
		return cm;
	}

}
