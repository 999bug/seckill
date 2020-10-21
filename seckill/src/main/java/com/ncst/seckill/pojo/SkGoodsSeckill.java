package com.ncst.seckill.pojo;



import java.util.Date;

public class SkGoodsSeckill {

    private long id;
    private long goodsId;
    private double seckillPrice;
    private long stockCount;
    private Date startDate;
    private Date endDate;
    private long version;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(long goodsId) {
    this.goodsId = goodsId;
  }

  public double getSeckillPrice() {
    return seckillPrice;
  }

  public void setSeckillPrice(double seckillPrice) {
    this.seckillPrice = seckillPrice;
  }

  public long getStockCount() {
    return stockCount;
  }

  public void setStockCount(long stockCount) {
    this.stockCount = stockCount;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}
