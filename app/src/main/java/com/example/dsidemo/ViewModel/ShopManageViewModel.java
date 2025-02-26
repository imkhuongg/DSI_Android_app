package com.example.dsidemo.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.dsidemo.models.product;
import com.example.dsidemo.repository.productRepository;

import java.util.List;

public class ShopManageViewModel extends ViewModel {
    private MutableLiveData<List<product>> products;
    private productRepository productRepository;
    private MutableLiveData<product> selectedProduct;


    public ShopManageViewModel() {
        products = new MutableLiveData<>();
        productRepository = new productRepository();
        selectedProduct = new MutableLiveData<>();
    }

    public LiveData<List<product>> getProducts() {
        return products;
    }
    public LiveData<product> getSelectedProduct() {
        return selectedProduct;
    }

    public void getProductShopManage(RequestQueue queue, String token){
        productRepository.getProductShopper(queue, token, new productRepository.RepositoryCallback() {
            @Override
            public void onSuccess(List<product> productList) {
                products.setValue(productList);
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });

    }
    public void setSelectedProduct(product productItem) {
        selectedProduct.setValue(productItem);
    }
}
