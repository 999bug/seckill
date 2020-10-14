package com.ncst.seckill.mapper;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Date 2020/10/14 20:05
 * @Author by LSY
 * @Description
 */
@Repository
@Mapper
public interface OrderMapper {
    @Select("select * from sk_order where user_id=#{userId} and goods_id=#{goodsId} ")
    SeckillUser getSecKillOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into sk_order(user_id,order_id,goods_id)values(#{userId},#{orderId},#{goodsId}) ")
    int insert(SkOrder skOrder);

    @Insert("insert into sk_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insertOrderInfo(SkOrderInfo orderInfo);
}
