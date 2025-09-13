package com.interview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("fide")
    @Expose
    private Object fide;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Object getFide() {
        return fide;
    }

    public void setFide(Object fide) {
        this.fide = fide;
    }
}
