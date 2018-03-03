package com.loya.android.arvark.models;

/**
 * Created by user on 12/1/2017.
 */

public class Category {

    private int imageResourceId;
    private String categoryText;

    public Category(int imageResourceId, String categoryText) {
        this.imageResourceId = imageResourceId;
        this.categoryText = categoryText;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getCategoryText() {
        return categoryText;
    }
}
