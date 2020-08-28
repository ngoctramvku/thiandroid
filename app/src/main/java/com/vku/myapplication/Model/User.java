package com.vku.myapplication.Model;

import java.io.Serializable;

public class User implements Serializable {
    String id;
    String fullname;
    String email;
    String username;
    String address;
    String phonenumber;
    String password;

    public User() {
    }

    public User(String id, String fullname, String email, String username, String address, String phonenumber, String password) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.address = address;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
