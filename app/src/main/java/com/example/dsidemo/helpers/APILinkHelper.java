package com.example.dsidemo.helpers;

public class APILinkHelper {
    private static final String BASE_URL = "http://192.168.0.101:8008/api/v1/";

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
    public static String deleteProduct(){return BASE_URL + "product/delete";}
    public static String signUp(){return BASE_URL + "auth/register";}
    public static String updateProduct(){return BASE_URL + "product/update";}
    public static String getAllProduct(){return BASE_URL + "product/productsAll";}
    public static String CheckShopper(){return BASE_URL + "shopper/check";}
    public static String getShopper(){return BASE_URL + "shopper/getShopper";}
    public static String ShopperRegistration(){return BASE_URL + "shopper/Registration";}
    public static String ShopperUpdateAvatar(){return BASE_URL + "shopper/updateAvatar";}
}
