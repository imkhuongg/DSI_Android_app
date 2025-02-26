package com.example.dsidemo.helpers.recycleviews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.views.MainScreen.Shop.ProductDetailActivity;

import java.util.List;

public class ProductAllRecycleAdapter extends RecyclerView.Adapter<ProductAllRecycleAdapter.productAllRecyleViewHoler> {

    private List<product> products;
    private Context context;

    public ProductAllRecycleAdapter(Context context, List<product> products) {
        this.context = context;
        this.products = products;
    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public class productAllRecyleViewHoler extends RecyclerView.ViewHolder{
        ImageView IMGProduct;
        TextView txt_nameProduct,txt_price, txt_rate;
        public productAllRecyleViewHoler(@NonNull View itemView) {
            super(itemView);

            IMGProduct = itemView.findViewById(R.id.img_product);
            txt_nameProduct = itemView.findViewById(R.id.name_products);
            txt_price = itemView.findViewById(R.id.txt_productPrice);
            txt_rate = itemView.findViewById(R.id.txt_productRate);
        }
    }

    @NonNull
    @Override
    public productAllRecyleViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_cart_list , parent ,false);
        productAllRecyleViewHoler holer = new productAllRecyleViewHoler(view);
         return holer;

    }

    @Override
    public void onBindViewHolder(@NonNull productAllRecyleViewHoler holder, int position) {
       product product = products.get(position);
        Glide
                .with(context)
                .load(APILinkHelper.getIMG() + products.get(position).getThumb())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.IMGProduct);
        holder.txt_nameProduct.setText(products.get(position).getName_product());
        String priceString = String.format("%,.2f" , products.get(position).getPrice()) + "đ";
        holder.txt_price.setText(priceString);
        String rateString = String.valueOf(products.get(position).getRate()) + "/5";
        holder.txt_rate.setText(rateString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , ProductDetailActivity.class);
                intent.putExtra("product",product);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
