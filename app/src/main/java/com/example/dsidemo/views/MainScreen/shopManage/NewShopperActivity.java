package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.viewpager.widget.ViewPager;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.ViewPager.ViewPagerAdapter;
import com.example.dsidemo.helpers.helper;

public class NewShopperActivity extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;
    ImageFilterButton btn_back,btn_next;
    Button btn_skip;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_newshopper);

        //BUTTON
        btn_skip = findViewById(R.id.btn_skip);
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);

        helper.hideSystemUI(getWindow().getDecorView());
        helper.setTouchEffect(btn_back);
        helper.setTouchEffect(btn_next);



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0) > 0){
                    mSlideViewPager.setCurrentItem(getItem(-1) , true);
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0) < 3) {
                    mSlideViewPager.setCurrentItem(getItem(1) , true);
                } else{
                    btn_next.setVisibility(View.INVISIBLE);
                }
                if(getItem(0) == 3){
                    btn_skip.setEnabled(true);
                    btn_skip.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.INVISIBLE);
                } else{
                    btn_skip.setVisibility(View.GONE);
                }

            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewShopperActivity.this, addShopperActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSlideViewPager.setAdapter(viewPagerAdapter);
        setUpindicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position){
        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for(int i = 0 ; i< dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText("\u2022");
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);

            if(position > 0){
                btn_back.setVisibility(View.VISIBLE);
                btn_skip.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
            }
            else {
                btn_back.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            };

            if (position == 3){
                btn_next.setVisibility(View.INVISIBLE);
                btn_skip.setEnabled(true);
                btn_skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getItem(int i){
        return mSlideViewPager.getCurrentItem() + i;
    }

}
