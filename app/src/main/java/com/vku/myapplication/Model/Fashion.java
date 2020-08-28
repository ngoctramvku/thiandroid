package com.vku.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fashion {

    @SerializedName("fasid")
    @Expose
    private String fasid;
    @SerializedName("ctg_id")
    @Expose
    private Integer ctgId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("nsx")
    @Expose
    private String nsx;
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

    public String getFasid() {
        return fasid;
    }

    public void setFasid(String fasid) {
        this.fasid = fasid;
    }

    public Integer getCtgId() {
        return ctgId;
    }

    public void setCtgId(Integer ctgId) {
        this.ctgId = ctgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
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
                "Fasid" + fasid +
                "CtgId" + ctgId +
                "Name" + name+'\'' +
                "Size" + size + '\'' +
                "Nsx" + nsx + '\'' +
                "Img" + img + '\'' +
                "Price" + price + '\'' +
                "CreatedAt" + createdAt +
                "UpdatedAt" + updatedAt +
                '}';
    }
}