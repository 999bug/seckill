package com.ncst.seckill.mapper;

import org.apache.ibatis.annotations.*;

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

	/**
	 *  通过id 获取 User 对象
	 * @param id id
	 * @return user对象
	 */
    @Select("select * from seckill_user where id = #{id}")
    SeckillUser getById(@Param("id") long id);

	/**
	 *  新增用户
	 * @param seckillUser user 对象
	 */
	@Insert("insert into seckill_user(id,nickname,password,salt,head,register_date,last_login_date,login_count) " +
            "values(#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate},#{loginCount})")
    void insertSecKill(SeckillUser seckillUser);

    /**
     * 更新密码
     *
     * @param seckillUser 秒杀对象
     */
    @Update("update seckill_user set password = #{password} where id=#{id}")
    void updateUser(SeckillUser seckillUser);
}
