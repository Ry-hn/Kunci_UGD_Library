package com.aditiaryohandoko.ugd_lib_x_yyyyy.model;

import com.google.gson.annotations.SerializedName;

public class YearlyPeak {

    @SerializedName("year")
    private int year;

    @SerializedName("value")
    private int value;

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }
}
