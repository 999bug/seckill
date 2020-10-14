package com.ncst.seckill.pojo;


/**
 * @author hp
 */
public class SkGoods {

  private long id;
  private String goodsName;
  private String goodsTitle;
  private String goodsImg;
  private String goodsDetail;
  private double goodsPrice;
  private long goodsStock;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }


  public String getGoodsTitle() {
    return goodsTitle;
  }

  public void setGoodsTitle(String goodsTitle) {
    this.goodsTitle = goodsTitle;
  }


  public String getGoodsImg() {
    return goodsImg;
  }

  public void setGoodsImg(String goodsImg) {
    this.goodsImg = goodsImg;
  }


  public String getGoodsDetail() {
    return goodsDetail;
  }

  public void setGoodsDetail(String goodsDetail) {
    this.goodsDetail = goodsDetail;
  }


  public double getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }


  public long getGoodsStock() {
    return goodsStock;
  }

  public void setGoodsStock(long goodsStock) {
    this.goodsStock = goodsStock;
  }

}
