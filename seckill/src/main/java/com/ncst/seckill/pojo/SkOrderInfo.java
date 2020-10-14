package com.ncst.seckill.pojo;


import java.util.Date;

public class SkOrderInfo {

  private long id;
  private long userId;
  private long goodsId;
  private long deliveryAddrId;
  private String goodsName;
  private long goodsCount;
  private double goodsPrice;
  private long orderChannel;
  private long status;
  private Date createDate;
  private Date payDate;


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

  public long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(long goodsId) {
    this.goodsId = goodsId;
  }

  public long getDeliveryAddrId() {
    return deliveryAddrId;
  }

  public void setDeliveryAddrId(long deliveryAddrId) {
    this.deliveryAddrId = deliveryAddrId;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public long getGoodsCount() {
    return goodsCount;
  }

  public void setGoodsCount(long goodsCount) {
    this.goodsCount = goodsCount;
  }

  public double getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  public long getOrderChannel() {
    return orderChannel;
  }

  public void setOrderChannel(long orderChannel) {
    this.orderChannel = orderChannel;
  }

  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
}
