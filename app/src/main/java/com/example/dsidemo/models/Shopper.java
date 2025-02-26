package com.example.dsidemo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Shopper {
    private int shopper_id;
    private int user_id;
    private String avatar;
    private String name_shop;
    private int phone;
    private String email;
    private String address;
    private int follower;
    private int total_sold;
    private BigDecimal total_revenue;
    private String created_at;
    private String updated_at;

    public Shopper(int shopper_id, int user_id, String avatar, String name_shop, int phone, String email, String address, int follower, int total_sold, BigDecimal total_revenue, String created_at, String updated_at) {
        this.shopper_id = shopper_id;
        this.user_id = user_id;
        this.avatar = avatar;
        this.name_shop = name_shop;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.follower = follower;
        this.total_sold = total_sold;
        this.total_revenue = total_revenue;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getTotal_sold() {
        return total_sold;
    }

    public void setTotal_sold(int total_sold) {
        this.total_sold = total_sold;
    }

    public BigDecimal getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(BigDecimal total_revenue) {
        this.total_revenue = total_revenue;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName_shop() {
        return name_shop;
    }

    public void setName_shop(String name_shop) {
        this.name_shop = name_shop;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShopper_id() {
        return shopper_id;
    }

    public void setShopper_id(int shopper_id) {
        shopper_id = shopper_id;
    }
}
