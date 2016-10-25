package com.hunter.dribbble.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserEntity implements Serializable {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("bio")
    private String mBio;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("links")
    private LinksEntity mLinks;
    @SerializedName("buckets_count")
    private int mBucketsCount;
    @SerializedName("comments_received_count")
    private int mCommentsReceivedCount;
    @SerializedName("followers_count")
    private int mFollowersCount;
    @SerializedName("followings_count")
    private int mFollowingsCount;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("likes_received_count")
    private int mLikesReceivedCount;
    @SerializedName("projects_count")
    private int mProjectsCount;
    @SerializedName("rebounds_received_count")
    private int mReboundsReceivedCount;
    @SerializedName("shots_count")
    private int mShotsCount;
    @SerializedName("teams_count")
    private int mTeamsCount;
    @SerializedName("can_upload_shot")
    private boolean mCanUploadShot;
    @SerializedName("type")
    private String mType;
    @SerializedName("pro")
    private boolean mPro;
    @SerializedName("buckets_url")
    private String mBucketsUrl;
    @SerializedName("followers_url")
    private String mFollowersUrl;
    @SerializedName("following_url")
    private String mFollowingUrl;
    @SerializedName("likes_url")
    private String mLikesUrl;
    @SerializedName("projects_url")
    private String mProjectsUrl;
    @SerializedName("shots_url")
    private String mShotsUrl;
    @SerializedName("teams_url")
    private String mTeamsUrl;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public static class Builder {

        private int id;
        private String name;
        private String username;
        private String htmlUrl;
        private String avatarUrl;
        private String bio;
        private String location;
        private LinksEntity links;
        private int bucketsCount;
        private int commentsReceivedCount;
        private int followersCount;
        private int followingsCount;
        private int likesCount;
        private int likesReceivedCount;
        private int projectsCount;
        private int reboundsReceivedCount;
        private int shotsCount;
        private int teamsCount;
        private boolean canUploadShot;
        private String type;
        private boolean pro;
        private String bucketsUrl;
        private String followersUrl;
        private String followingUrl;
        private String likesUrl;
        private String projectsUrl;
        private String shotsUrl;
        private String teamsUrl;
        private String createdAt;
        private String updatedAt;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public Builder setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setLinks(LinksEntity links) {
            this.links = links;
            return this;
        }

        public Builder setBucketsCount(int bucketsCount) {
            this.bucketsCount = bucketsCount;
            return this;
        }

        public Builder setCommentsReceivedCount(int commentsReceivedCount) {
            this.commentsReceivedCount = commentsReceivedCount;
            return this;
        }

        public Builder setFollowersCount(int followersCount) {
            this.followersCount = followersCount;
            return this;
        }

        public Builder setFollowingsCount(int followingsCount) {
            this.followingsCount = followingsCount;
            return this;
        }

        public Builder setLikesCount(int likesCount) {
            this.likesCount = likesCount;
            return this;
        }

        public Builder setLikesReceivedCount(int likesReceivedCount) {
            this.likesReceivedCount = likesReceivedCount;
            return this;
        }

        public Builder setProjectsCount(int projectsCount) {
            this.projectsCount = projectsCount;
            return this;
        }

        public Builder setReboundsReceivedCount(int reboundsReceivedCount) {
            this.reboundsReceivedCount = reboundsReceivedCount;
            return this;
        }

        public Builder setShotsCount(int shotsCount) {
            this.shotsCount = shotsCount;
            return this;
        }

        public Builder setTeamsCount(int teamsCount) {
            this.teamsCount = teamsCount;
            return this;
        }

        public Builder setCanUploadShot(boolean canUploadShot) {
            this.canUploadShot = canUploadShot;
            return this;
        }

        public Builder setYype(String type) {
            this.type = type;
            return this;
        }

        public Builder setPro(boolean pro) {
            this.pro = pro;
            return this;
        }

        public Builder setBucketsUrl(String bucketsUrl) {
            this.bucketsUrl = bucketsUrl;
            return this;
        }

        public Builder setFollowersUrl(String followersUrl) {
            this.followersUrl = followersUrl;
            return this;
        }

        public Builder setFollowingUrl(String followingUrl) {
            this.followingUrl = followingUrl;
            return this;
        }

        public Builder setLikesUrl(String likesUrl) {
            this.likesUrl = likesUrl;
            return this;
        }

        public Builder setProjectsUrl(String projectsUrl) {
            this.projectsUrl = projectsUrl;
            return this;
        }

        public Builder setShotsUrl(String shotsUrl) {
            this.shotsUrl = shotsUrl;
            return this;
        }

        public Builder setTeamsUrl(String teamsUrl) {
            this.teamsUrl = teamsUrl;
            return this;
        }

        public Builder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(id, name, username, htmlUrl, avatarUrl, bio, location, links, bucketsCount,
                    commentsReceivedCount, followersCount, followingsCount, likesCount, likesReceivedCount,
                    projectsCount, reboundsReceivedCount, shotsCount, teamsCount, canUploadShot, type, pro, bucketsUrl,
                    followersUrl, followingUrl, likesUrl, projectsUrl, shotsUrl, teamsUrl, createdAt, updatedAt);
        }
    }

    public UserEntity(int id, String name, String username, String htmlUrl, String avatarUrl, String bio,
                      String location, LinksEntity links, int bucketsCount, int commentsReceivedCount,
                      int followersCount, int followingsCount, int likesCount, int likesReceivedCount,
                      int projectsCount, int reboundsReceivedCount, int shotsCount, int teamsCount,
                      boolean canUploadShot, String type, boolean pro, String bucketsUrl, String followersUrl,
                      String followingUrl, String likesUrl, String projectsUrl, String shotsUrl, String teamsUrl,
                      String createdAt, String updatedAt) {
        mId = id;
        mName = name;
        mUsername = username;
        mHtmlUrl = htmlUrl;
        mAvatarUrl = avatarUrl;
        mBio = bio;
        mLocation = location;
        mLinks = links;
        mBucketsCount = bucketsCount;
        mCommentsReceivedCount = commentsReceivedCount;
        mFollowersCount = followersCount;
        mFollowingsCount = followingsCount;
        mLikesCount = likesCount;
        mLikesReceivedCount = likesReceivedCount;
        mProjectsCount = projectsCount;
        mReboundsReceivedCount = reboundsReceivedCount;
        mShotsCount = shotsCount;
        mTeamsCount = teamsCount;
        mCanUploadShot = canUploadShot;
        mType = type;
        mPro = pro;
        mBucketsUrl = bucketsUrl;
        mFollowersUrl = followersUrl;
        mFollowingUrl = followingUrl;
        mLikesUrl = likesUrl;
        mProjectsUrl = projectsUrl;
        mShotsUrl = shotsUrl;
        mTeamsUrl = teamsUrl;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public LinksEntity getLinks() {
        return mLinks;
    }

    public void setLinks(LinksEntity links) {
        mLinks = links;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
    }

    public int getCommentsReceivedCount() {
        return mCommentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        mCommentsReceivedCount = commentsReceivedCount;
    }

    public int getFollowersCount() {
        return mFollowersCount;
    }

    public void setFollowersCount(int followersCount) {
        mFollowersCount = followersCount;
    }

    public int getFollowingsCount() {
        return mFollowingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        mFollowingsCount = followingsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getLikesReceivedCount() {
        return mLikesReceivedCount;
    }

    public void setLikesReceivedCount(int likesReceivedCount) {
        mLikesReceivedCount = likesReceivedCount;
    }

    public int getProjectsCount() {
        return mProjectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        mProjectsCount = projectsCount;
    }

    public int getReboundsReceivedCount() {
        return mReboundsReceivedCount;
    }

    public void setReboundsReceivedCount(int reboundsReceivedCount) {
        mReboundsReceivedCount = reboundsReceivedCount;
    }

    public int getShotsCount() {
        return mShotsCount;
    }

    public void setShotsCount(int shotsCount) {
        mShotsCount = shotsCount;
    }

    public int getTeamsCount() {
        return mTeamsCount;
    }

    public void setTeamsCount(int teamsCount) {
        mTeamsCount = teamsCount;
    }

    public boolean isCanUploadShot() {
        return mCanUploadShot;
    }

    public void setCanUploadShot(boolean canUploadShot) {
        mCanUploadShot = canUploadShot;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public boolean isPro() {
        return mPro;
    }

    public void setPro(boolean pro) {
        mPro = pro;
    }

    public String getBucketsUrl() {
        return mBucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        mBucketsUrl = bucketsUrl;
    }

    public String getFollowersUrl() {
        return mFollowersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        mFollowersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return mFollowingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        mFollowingUrl = followingUrl;
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

    public String getShotsUrl() {
        return mShotsUrl;
    }

    public void setShotsUrl(String shotsUrl) {
        mShotsUrl = shotsUrl;
    }

    public String getTeamsUrl() {
        return mTeamsUrl;
    }

    public void setTeamsUrl(String teamsUrl) {
        mTeamsUrl = teamsUrl;
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

}
