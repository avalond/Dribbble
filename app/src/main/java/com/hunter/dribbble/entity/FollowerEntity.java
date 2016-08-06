package com.hunter.dribbble.entity;

import com.google.gson.annotations.SerializedName;

public class FollowerEntity {

    @SerializedName("id")
    private int mId;
    @SerializedName("created_at")
    private String         mCreatedAt;
    @SerializedName("follower")
    private UserEntity mFollower;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public UserEntity getFollower() {
        return mFollower;
    }

    public void setFollower(UserEntity follower) {
        mFollower = follower;
    }


}
