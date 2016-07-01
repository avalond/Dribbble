package com.hunter.dribbble.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShotsEntity implements Serializable {

    @SerializedName("id")
    private int          mId;
    @SerializedName("title")
    private String       mTitle;
    @SerializedName("description")
    private String       mDescription;
    @SerializedName("width")
    private int          mWidth;
    @SerializedName("height")
    private int          mHeight;
    @SerializedName("images")
    private ImagesEntity mImages;
    @SerializedName("views_count")
    private int          mViewsCount;
    @SerializedName("likes_count")
    private int          mLikesCount;
    @SerializedName("comments_count")
    private int          mCommentsCount;
    @SerializedName("attachments_count")
    private int          mAttachmentsCount;
    @SerializedName("rebounds_count")
    private int          mReboundsCount;
    @SerializedName("buckets_count")
    private int          mBucketsCount;
    @SerializedName("created_at")
    private String       mCreatedAt;
    @SerializedName("updated_at")
    private String       mUpdatedAt;
    @SerializedName("html_url")
    private String       mHtmlUrl;
    @SerializedName("attachments_url")
    private String       mAttachmentsUrl;
    @SerializedName("buckets_url")
    private String       mBucketsUrl;
    @SerializedName("comments_url")
    private String       mCommentsUrl;
    @SerializedName("likes_url")
    private String       mLikesUrl;
    @SerializedName("projects_url")
    private String       mProjectsUrl;
    @SerializedName("rebounds_url")
    private String       mReboundsUrl;
    @SerializedName("animated")
    private boolean      mAnimated;
    @SerializedName("user")
    private UserEntity   mUser;
    @SerializedName("team")
    private TeamEntity   mTeam;
    @SerializedName("tags")
    private List<String> mTags;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public ImagesEntity getImages() {
        return mImages;
    }

    public void setImages(ImagesEntity images) {
        mImages = images;
    }

    public int getViewsCount() {
        return mViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        mViewsCount = viewsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        mCommentsCount = commentsCount;
    }

    public int getAttachmentsCount() {
        return mAttachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        mAttachmentsCount = attachmentsCount;
    }

    public int getReboundsCount() {
        return mReboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        mReboundsCount = reboundsCount;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public String getAttachmentsUrl() {
        return mAttachmentsUrl;
    }

    public void setAttachmentsUrl(String attachmentsUrl) {
        mAttachmentsUrl = attachmentsUrl;
    }

    public String getBucketsUrl() {
        return mBucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        mBucketsUrl = bucketsUrl;
    }

    public String getCommentsUrl() {
        return mCommentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        mCommentsUrl = commentsUrl;
    }

    public String getLikesUrl() {
        return mLikesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        mLikesUrl = likesUrl;
    }

    public String getProjectsUrl() {
        return mProjectsUrl;
    }

    public void setProjectsUrl(String projectsUrl) {
        mProjectsUrl = projectsUrl;
    }

    public String getReboundsUrl() {
        return mReboundsUrl;
    }

    public void setReboundsUrl(String reboundsUrl) {
        mReboundsUrl = reboundsUrl;
    }

    public boolean isAnimated() {
        return mAnimated;
    }

    public void setAnimated(boolean animated) {
        mAnimated = animated;
    }

    public UserEntity getUser() {
        return mUser;
    }

    public void setUser(UserEntity user) {
        mUser = user;
    }

    public TeamEntity getTeam() {
        return mTeam;
    }

    public void setTeam(TeamEntity team) {
        mTeam = team;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }

}
