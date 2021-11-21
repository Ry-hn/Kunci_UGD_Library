package com.aditiaryohandoko.ugd_lib_x_yyyyy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("urlImage")
    private String urlImage;

    @SerializedName("currency")
    private String currency;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("peakYearlyOnlineUser")
    private List<YearlyPeak> peakYearlyOnlineUser;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFormattedPrice() {
        if(getPrice() == 0 )
            return "Free to Play";
        return getCurrency() + getPrice();
    }

    public String getDescription() {
        return description;
    }

    public List<YearlyPeak> getPeakYearlyOnlineUser() {
        return peakYearlyOnlineUser;
    }
}
