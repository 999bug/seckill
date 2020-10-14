package com.ncst.seckill.pojo;


public class SkOrder {

  private long id;
  private long userId;
  private long orderId;
  private long goodsId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(long goodsId) {
    this.goodsId = goodsId;
  }

  @Override
  public String toString() {
    return "SkOrder{" +
            "id=" + id +
            ", userId=" + userId +
            ", orderId=" + orderId +
            ", goodsId=" + goodsId +
            '}';
  }
}
