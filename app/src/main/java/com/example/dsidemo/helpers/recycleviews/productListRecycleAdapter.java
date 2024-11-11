package com.example.dsidemo.helpers.recycleviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dsidemo.R;
import com.example.dsidemo.models.product;

import java.util.List;

public class productListRecycleAdapter extends RecyclerView.Adapter<productListRecycleAdapter.productListViewHolder> {

    List<product> productList;
    Context context;

    public productListRecycleAdapter(List<product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public List<product> getProductList() {
        return productList;
    }

    public void setProductList(List<product> productList) {
        this.productList = productList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class productListViewHolder extends RecyclerView.ViewHolder{

        ImageView img_product;
        TextView nameProduct_txt,price_txt,brandName_txt,rate_txt;
        Button state_btn,cancel_btn;

        public productListViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            nameProduct_txt = itemView.findViewById(R.id.nameProduct_txt);
            price_txt = itemView.findViewById(R.id.profits_txt);
            brandName_txt = itemView.findViewById(R.id.dateReserved_txt);
            rate_txt = itemView.findViewById(R.id.state_txt);

            state_btn = itemView.findViewById(R.id.state_btn);
            cancel_btn = itemView.findViewById(R.id.cancel_btn);


        }
    }

    @NonNull
    @Override
    public productListRecycleAdapter.productListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shop_manage_items , parent ,false);
        productListViewHolder holder = new productListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull productListRecycleAdapter.productListViewHolder holder, int position) {
        holder.nameProduct_txt.setText(productList.get(position).getName_product());
        holder.price_txt.setText("Giá bán: " + productList.get(position).getStringPrice());
        holder.brandName_txt.setText("Thương hiệu: " +productList.get(position).getName_brand());
        holder.rate_txt.setText("Đánh giá: "+productList.get(position).getRate());
        Glide.with(this.context).load(productList.get(position).getThumb()).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
