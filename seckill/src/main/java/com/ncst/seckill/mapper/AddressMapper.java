package com.ncst.seckill.mapper;

import com.ncst.seckill.pojo.SkAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Date 2020/10/20 20:45
 * @Author by LSY
 * @Description
 */
@Repository
@Mapper
public interface AddressMapper{

    @Select("select * from sk_address  where uid=#{ids}")
    SkAddress getAddressByUid(@Param("ids") long id);

    @Insert("insert into sk_address(uid,nickname,address,phone,created_time,modified_time)values(#{uid},#{nickname},#{address},#{phone},#{createdTime},#{modifiedTime})")
    void insert(SkAddress skAddress);
}
