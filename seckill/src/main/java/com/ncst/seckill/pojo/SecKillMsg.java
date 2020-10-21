package com.ncst.seckill.pojo;

/**
 * @Date 2020/10/18 9:37
 * @Author by LSY
 * @Description
 */
public class SecKillMsg {
    private SkUser skUser;
    private long goodsId;

    public SkUser getSkUser() {
        return skUser;
    }

    public void setSkUser(SkUser skUser) {
        this.skUser = skUser;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
