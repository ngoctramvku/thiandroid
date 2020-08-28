package com.vku.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("foodid")
    @Expose
    private String foodid;
    @SerializedName("ctg_id")
    @Expose
    private Integer ctgId;
    @SerializedName("foodname")
    @Expose
    private String foodname;
    @SerializedName("khoiluong")
    @Expose
    private Integer khoiluong;
    @SerializedName("nsx")
    @Expose
    private String nsx;
    @SerializedName("ngsx")
    @Expose
    private String ngsx;
    @SerializedName("hsd")
    @Expose
    private String hsd;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public Integer getCtgId() {
        return ctgId;
    }

    public void setCtgId(Integer ctgId) {
        this.ctgId = ctgId;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Integer getKhoiluong() {
        return khoiluong;
    }

    public void setKhoiluong(Integer khoiluong) {
        this.khoiluong = khoiluong;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public String getNgsx() {
        return ngsx;
    }

    public void setNgsx(String ngsx) {
        this.ngsx = ngsx;
    }

    public String getHsd() {
        return hsd;
    }

    public void setHsd(String hsd) {
        this.hsd = hsd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String toString(){
        return "data{" +
                "Foodid" + foodid +
                "Foodname" + foodname + '\'' +
                "Khoiluong" + khoiluong + '\'' +
                "Nsx" + nsx + '\'' +
                "Ngsx" + ngsx + '\'' +
                "Hsd" + hsd +  '\'' +
                "Img" + img + '\'' +
                "Price" + price + '\'' +
                "CreatedAt" + createdAt +
                "UpdatedAt" + updatedAt +
                '}'
                ;
    }

}
