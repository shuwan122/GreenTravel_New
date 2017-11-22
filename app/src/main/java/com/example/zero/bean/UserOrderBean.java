package com.example.zero.bean;

/**
 * Created by kazu_0122 on 2017/11/16.
 */

public class UserOrderBean {
    private int icon;
    private int state;
    //0--未付款 1--未发货 2--未收货 3--未取货 4--交易完成（未评价） 5--交易完成（已评价）
    private String shop_name;
    private String goods_name;
    private String goods_type;
    private String goods_pic;
    private int goods_count;
    private double goods_price;
    private String item;
    private String cus_msg;
    public static final String GOODS = "goods";
    public static final String SHOP = "shop";
    public static final String ACTION = "action";

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setShopName(String name) {
        this.shop_name = name;
    }

    public void setGoodsName(String name) {
        this.goods_name = name;
    }

    public void setGoodsType(String type) {
        this.goods_type = type;
    }

    public void setGoodsPic(String pic) {
        this.goods_pic = pic;
    }

    public void setGoodsCount(int count) {
        this.goods_count = count;
    }

    public void setGoodsPrice(double price) {
        this.goods_price = price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setCusMsg(String msg) {
        this.cus_msg = msg;
    }

    public String getCusMsg() {
        return cus_msg;
    }

    public String getShopName() {
        return shop_name;
    }

    public String getGoodsName() {
        return goods_name;
    }

    public String getGoodsType() {
        return goods_type;
    }

    public String getGoodsPic() {
        return goods_pic;
    }

    public int getGoodsCount() {
        return goods_count;
    }

    public double getGoodsPrice() {
        return goods_price;
    }

    public String getItem() {
        return item;
    }

    public int getIcon() {
        return icon;
    }

    public int getState() {
        return state;
    }


}
