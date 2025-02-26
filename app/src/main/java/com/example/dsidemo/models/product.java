package com.example.dsidemo.models;

import java.io.Serializable;

public class product implements Serializable {
    private int product_id;
    private  String name_product;
    private double price;
    private int user_id;
    private String description;
    private double rate;
    private String name_brand;
    private String thumb;
    private int shopper_id;
    private int quantity_sold;
    private String created_at;
    private String updated_at;


    public product(int product_id, String name_product, double price, int user_id, String description, double rate, String name_brand, String thumb, int quantity_sold,int shopper_id,String created_at ,String updated_at) {
        this.product_id = product_id;
        this.name_product = name_product;
        this.price = price;
        this.user_id = user_id;
        this.description = description;
        this.rate = rate;
        this.name_brand = name_brand;
        this.thumb = thumb;
        this.quantity_sold = quantity_sold;
        this.shopper_id = shopper_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public product() {}

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public double getPrice() {
        return  price;
    }
    public String getStringPrice(){
        return Double.toString(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public int getShopper_id() {
        return shopper_id;
    }

    public void setShopper_id(int shopper_id) {
        this.shopper_id = shopper_id;
    }

    public String getCreated_at() {
        if(created_at == null) return " ";
        else return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        if(updated_at == null) return  " ";
        else return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
