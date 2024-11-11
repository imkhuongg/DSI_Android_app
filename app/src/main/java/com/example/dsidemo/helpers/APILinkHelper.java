package com.example.dsidemo.helpers;

public class APILinkHelper {
    private static final String BASE_URL = "http://192.168.0.102:8007/api/v1/";

    public static String authUserApiUri(){
        return BASE_URL + "auth/login";
    }
    public static String getIMG(){
        return BASE_URL + "image/download/";
    }
    public static String getProduts(){
        return BASE_URL + "product/get_shopper_product";
    }

}
