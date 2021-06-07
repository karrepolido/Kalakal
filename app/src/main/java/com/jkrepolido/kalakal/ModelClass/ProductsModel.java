package com.jkrepolido.kalakal.ModelClass;

public class ProductsModel {
    String pImage, pName, pDesc, pTrader, pTraderPhoto, pDistance;

    public ProductsModel() {}

    public ProductsModel(String pImage, String pName, String pDesc, String pTrader, String pTraderPhoto, String pDistance) {
        this.pImage = pImage;
        this.pName = pName;
        this.pDesc = pDesc;
        this.pTrader = pTrader;
        this.pTraderPhoto = pTraderPhoto;
        this.pDistance = pDistance;
    }

    public String getpImage() {
        return pImage;
    }

    public String getpName() {
        return pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public String getpTrader() {
        return pTrader;
    }

    public String getpTraderPhoto() {
        return pTraderPhoto;
    }

    public String getpDistance() {
        return pDistance;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public void setpTrader(String pTrader) {
        this.pTrader = pTrader;
    }

    public void setpTraderPhoto(String pTraderPhoto) {
        this.pTraderPhoto = pTraderPhoto;
    }

    public void setpDistance(String pDistance) {
        this.pDistance = pDistance;
    }
}
