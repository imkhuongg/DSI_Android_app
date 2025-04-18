package com.example.dsidemo.helpers.ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.dsidemo.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int images[] = {
            R.drawable.background_money,
            R.drawable.background_first_order,
            R.drawable.background3,
            R.drawable.background4
    };
    int headings[] = {
            R.string.first_money,
            R.string.first_order_title,
            R.string.payment_title,
            R.string.pay_everywhere_title,

    };
    int descriptions[] = {
            R.string.first_money_description,
            R.string.first_order_desc,
            R.string.payment_desc,
            R.string.pay_everywhere_desc,
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view  == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slideImage = (ImageView) view.findViewById(R.id.ImgSlider);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        TextView desc = (TextView) view.findViewById(R.id.txt_description);

        slideImage.setImageResource(images[position]);
        title.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);

    }
}
