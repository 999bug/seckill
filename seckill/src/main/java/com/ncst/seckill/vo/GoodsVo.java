package com.ncst.seckill.vo;

import com.ncst.seckill.pojo.SkGoods;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Date 2020/10/14 15:58
 * @Author by LSY
 * @Description
 */
@Getter
@Setter
public class GoodsVo extends SkGoods {
    private Double seckillPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;
    private int version;

    @Override
    public String toString() {
        return "GoodsVo{" +
                "seckillPrice=" + seckillPrice +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
