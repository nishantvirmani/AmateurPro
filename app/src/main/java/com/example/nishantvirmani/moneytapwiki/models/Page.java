
package com.example.nishantvirmani.moneytapwiki.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("pageid")
    @Expose
    private Integer pageid;
    @SerializedName("ns")
    @Expose
    private Integer ns;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("terms")
    @Expose
    private Terms terms;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    private String titleUrl;

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitleUrl() {
        titleUrl = title.replaceAll(" ", "_");
        return titleUrl;
    }
}
