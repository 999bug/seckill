package com.ncst.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
@MapperScan("com.ncst.seckill.mapper")
@SpringBootApplication
public class MainApplication  {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}


}
