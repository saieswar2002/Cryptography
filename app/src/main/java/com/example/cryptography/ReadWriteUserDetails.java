package com.example.cryptography;

public class ReadWriteUserDetails {
    public String username, email_id, mobile;

    public ReadWriteUserDetails(){}
    public ReadWriteUserDetails(String textUsername, String textEmail_id, String textMobile){
        this.username = textUsername;
        this.email_id = textEmail_id;
        this.mobile = textMobile;
    }
}
