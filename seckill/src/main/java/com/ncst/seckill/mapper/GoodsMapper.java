package com.ncst.seckill.mapper;

import com.ncst.seckill.pojo.SkGoodsSeckill;
import com.ncst.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2020/10/14 16:01
 * @Author by LSY
 * @Description
 */
@Repository
@Mapper
public interface GoodsMapper {

    @Select("SELECT sg.*,skd.start_date,skd.end_date,skd.seckill_price,skd.stock_count from  sk_goods_seckill skd LEFT JOIN sk_goods sg ON skd.id=sg.id ")
    List<GoodsVo> listGoodsVo();


    @Select("SELECT sg.*,skd.start_date,skd.end_date,skd.seckill_price,skd.stock_count from  sk_goods_seckill skd LEFT JOIN sk_goods sg ON skd.id=sg.id where sg.id=#{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update sk_goods_seckill set stock_count=stock_count-1 where goods_id=#{goodsId}")
    void reduceStock(SkGoodsSeckill goods);
}
