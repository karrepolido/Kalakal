package com.jkrepolido.kalakal.ModelClass;

import android.widget.EditText;

public class UsersModel {
    String uImage, uName, uAddress, uEmail, uPassword, usID;

    public UsersModel() {}

    public UsersModel(String uImage, String uName, String uAddress, String uEmail, String uPassword, String usID) {
        this.uImage = uImage;
        this.uName = uName;
        this.uAddress = uAddress;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.usID = usID;
    }

    public String getuImage() {
        return uImage;
    }

    public String getuName() {
        return uName;
    }

    public String getuAddress() {
        return uAddress;
    }

    public String getuEmail() {
        return uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public String getusID() {
        return usID;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public void setusID(String usID) {
        this.usID = usID;
    }
}
