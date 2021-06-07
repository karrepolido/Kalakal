package com.jkrepolido.kalakal.ModelClass;

public class CategoriesModel {
    String catImage, catName;

    public CategoriesModel() {}

    public CategoriesModel(String catImage, String catName) {
        this.catImage = catImage;
        this.catName = catName;
    }

    public String getcatImage() {
        return catImage;
    }

    public String getcatName() {
        return catName;
    }

    public void setcatImage(String catImage) {
        this.catImage = catImage;
    }

    public void setcatName(String catName) {
        this.catName = catName;
    }
}
