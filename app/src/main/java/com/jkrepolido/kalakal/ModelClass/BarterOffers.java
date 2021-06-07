package com.jkrepolido.kalakal.ModelClass;

public class BarterOffers {
    String bItem, bValue, bCondition, bDetails, bImage;

    public BarterOffers() {}

    public BarterOffers(String bItem, String bValue, String bCondition, String bDetails, String bImage) {
        this.bItem = bItem;
        this.bValue = bValue;
        this.bCondition = bCondition;
        this.bDetails = bDetails;
        this.bImage = bImage;
    }

    public String getbItem() {
        return bItem;
    }

    public String getbValue() {
        return bValue;
    }

    public String getbCondition() {
        return bCondition;
    }

    public String getbDetails() {
        return bDetails;
    }

    public String getbImageURL() {
        return bImage;
    }

    public void setbItem(String bItem) {
        this.bItem = bItem;
    }

    public void setbValue(String bValue) {
        this.bValue = bValue;
    }

    public void setbCondition(String bCondition) {
        this.bCondition = bCondition;
    }

    public void setbDetails(String bDetails) {
        this.bDetails = bDetails;
    }

    public void setbImageURL(String bImage) {
        this.bImage = bImage;
    }
}
