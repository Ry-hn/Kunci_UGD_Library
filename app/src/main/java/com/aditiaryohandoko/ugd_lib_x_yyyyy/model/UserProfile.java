package com.aditiaryohandoko.ugd_lib_x_yyyyy.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("username")
    private String username;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
