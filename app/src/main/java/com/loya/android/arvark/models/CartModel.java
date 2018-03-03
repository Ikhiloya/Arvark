package com.loya.android.arvark.models;

/**
 * Created by user on 12/5/2017.
 */

public class CartModel {
    private String desc;
    private int imageResourceId;

    public CartModel(String desc, int imageResourceId) {
        this.desc = desc;
        this.imageResourceId = imageResourceId;
    }

    public String getDesc() {
        return desc;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
