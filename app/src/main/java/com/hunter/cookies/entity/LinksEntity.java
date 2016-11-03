package com.hunter.cookies.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LinksEntity implements Serializable {

    @SerializedName("web")
    private String mWeb;
    @SerializedName("twitter")
    private String mTwitter;

    public String getWeb() {
        return mWeb;
    }

    public void setWeb(String web) {
        mWeb = web;
    }

    public String getTwitter() {
        return mTwitter;
    }

    public void setTwitter(String twitter) {
        mTwitter = twitter;
    }
}
