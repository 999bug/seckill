package com.ncst.seckill.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ncst.seckill.pojo.SeckillUser;
import org.springframework.stereotype.Repository;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 用户登录的mapper
 */
@Mapper
@Repository
public interface UserMapper {
	
	@Select("select * from seckill_user where id = #{id}")
	public SeckillUser getById(@Param("id")long id);

	@Insert("insert into seckill_user(id,nickname,password,salt,head,register_date,last_login_date,login_count) " +
			"values(#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate},#{loginCount})")
	void insertSeckill(SeckillUser seckillUser);
}
