package com.ncst.seckill.pojo;

/**
 * @Date 2020/10/18 9:37
 * @Author by LSY
 * @Description
 */
public class SecKillMsg {
    private SeckillUser seckillUser;
    private long goodsId;

    public SeckillUser getSeckillUser() {
        return seckillUser;
    }

    public void setSeckillUser(SeckillUser seckillUser) {
        this.seckillUser = seckillUser;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
