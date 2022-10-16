package com.example.quotify.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("portfolio")
    @Expose
    private String portfolio;

    @SerializedName("html")
    @Expose
    private String html;

    /**
     * No args constructor for use in serialization
     *
     */
    public Links() {
    }

    /**
     * @param html
     * @param portfolio
     */
    public Links(String portfolio, String html) {
        super();
        this.portfolio = portfolio;
        this.html = html;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}