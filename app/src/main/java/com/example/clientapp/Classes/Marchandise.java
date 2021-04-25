package com.example.clientapp.Classes;
public class Marchandise {
    private String mImageUrl;

    private String description;
    private String price;

    public Marchandise(){

    }

    public Marchandise(String mImageUrl, String description, String price) {
        this.mImageUrl = mImageUrl;

        this.description = description;
        this.price= price;

    }

    public  String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}