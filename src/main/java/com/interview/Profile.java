package com.interview;

import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Profile {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("fideid")
    @Expose
    private Long fideid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("byear")
    @Expose
    private Long byear;
    @SerializedName("bdate")
    @Expose
    private String bdate;
    @SerializedName("ddate")
    @Expose
    private String ddate;
    @SerializedName("bplace")
    @Expose
    private String bplace;
    @SerializedName("dplace")
    @Expose
    private String dplace;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("wiki_link")
    @Expose
    private String wikiLink;
    @SerializedName("facebook_link")
    @Expose
    private String facebookLink;
    @SerializedName("twitter_link")
    @Expose
    private String twitterLink;
    @SerializedName("instagram_link")
    @Expose
    private String instagramLink;
    @SerializedName("file_id")
    @Expose
    private Long fileId;
    @SerializedName("file_copyright")
    @Expose
    private String fileCopyright;
    @SerializedName("lastupdate")
    @Expose
    private String lastupdate;

    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public Long getFideid() {
    return fideid;
    }

    public void setFideid(Long fideid) {
    this.fideid = fideid;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }

    public String getCountry() {
    return country;
    }

    public void setCountry(String country) {
    this.country = country;
    }

    public Long getByear() {
    return byear;
    }

    public void setByear(Long byear) {
    this.byear = byear;
    }

    public String getBdate() {
    return bdate;
    }

    public void setBdate(String bdate) {
    this.bdate = bdate;
    }

    public String getDdate() {
    return ddate;
    }

    public void setDdate(String ddate) {
    this.ddate = ddate;
    }

    public String getBplace() {
    return bplace;
    }

    public void setBplace(String bplace) {
    this.bplace = bplace;
    }

    public String getDplace() {
    return dplace;
    }

    public void setDplace(String dplace) {
    this.dplace = dplace;
    }

    public String getBio() {
    return bio;
    }

    public void setBio(String bio) {
    this.bio = bio;
    }

    public String getWikiLink() {
    return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
    this.wikiLink = wikiLink;
    }

    public String getFacebookLink() {
    return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
    this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
    return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
    this.twitterLink = twitterLink;
    }

    public String getInstagramLink() {
    return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
    this.instagramLink = instagramLink;
    }

    public Long getFileId() {
    return fileId;
    }

    public void setFileId(Long fileId) {
    this.fileId = fileId;
    }

    public String getFileCopyright() {
    return fileCopyright;
    }

    public void setFileCopyright(String fileCopyright) {
    this.fileCopyright = fileCopyright;
    }

    public String getLastupdate() {
    return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
    this.lastupdate = lastupdate;
    }
}
