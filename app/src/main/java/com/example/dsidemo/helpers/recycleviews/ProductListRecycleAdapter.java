package com.example.dsidemo.helpers.recycleviews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dsidemo.R;
import com.example.dsidemo.ViewModel.ShopManageViewModel;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.views.MainScreen.shopManage.itemProductDetailManage;

import java.util.List;

public class ProductListRecycleAdapter extends RecyclerView.Adapter<ProductListRecycleAdapter.productListViewHolder> {

    List<product> productList;
    Context context;
    private ShopManageViewModel viewModel;
    private AdapterView.OnItemClickListener listener;

    public ProductListRecycleAdapter(List<product> productList, Context context, ShopManageViewModel viewModel ) {
        this.productList = productList;
        this.context = context;
        this.viewModel = viewModel;
    }

    public List<product> getProductList() {
        return productList;
    }

    public void setProductList(List<product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
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

        }
    }

    @NonNull
    @Override
    public ProductListRecycleAdapter.productListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shop_manage_items , parent ,false);
        productListViewHolder holder = new productListViewHolder(view);

        return holder;
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListRecycleAdapter.productListViewHolder holder, int position) {
        product product = productList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(APILinkHelper.getIMG() + productList.get(position).getThumb())
                .override(200, 300)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.img_product);
        holder.nameProduct_txt.setText(productList.get(position).getName_product());
        holder.price_txt.setText("Giá bán: " + productList.get(position).getStringPrice());
        holder.brandName_txt.setText("Thương hiệu: " +productList.get(position).getName_brand());
        holder.rate_txt.setText("Đánh giá: "+productList.get(position).getRate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = holder.itemView.getContext();
                Intent intent = new Intent(context , itemProductDetailManage.class);
                intent.putExtra("id_product" , product.getProduct_id());
                intent.putExtra("name_product" , product.getName_product());
                intent.putExtra("name_brand" , product.getName_brand());
                intent.putExtra("price" , product.getPrice());
                intent.putExtra("description" , product.getDescription());
                intent.putExtra("rate" , product.getRate());
                intent.putExtra("shopper_id" , product.getShopper_id());
                intent.putExtra("createdAt" , product.getCreated_at());
                intent.putExtra("updatedAt" , product.getUpdated_at());
                intent.putExtra("thumb" , product.getThumb());

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
