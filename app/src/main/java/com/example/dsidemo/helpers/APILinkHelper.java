package com.example.dsidemo.helpers;

public class APILinkHelper {
    private static final String BASE_URL = "http://172.16.1.44:8008/api/v1/";

public static String getBaseURL(){
    return BASE_URL;
}

    public static String authUserApiUri(){
        return BASE_URL + "auth/login";
    }
    public static String getIMG(){
        return BASE_URL + "image/download/";
    }
    public static String getProduts(){
        return BASE_URL + "product/get_shopper_product";
    }
    public static String createProduct(){
        return BASE_URL + "product/create";
    }
    public static String postImg(){
        return BASE_URL + "image/upload";
    }

}
