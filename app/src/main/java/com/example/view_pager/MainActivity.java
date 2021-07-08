package com.example.view_pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.view_pager.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private TextView[] dots;
    private int[] layouts;
    private String[] backgroundColors;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        viewPager = mBinding.viewPager;
        btnNext = mBinding.btnNext;
        btnSkip = mBinding.btnSkip;
        dotLayout = mBinding.layoutDots;

        // Layout of all welcome sliders
        // Add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4
        };

        backgroundColors = new String[]{
                "#f64c73",
                "#20d2bb",
                "#3395ff",
                "#c873f4"
        };

        // Adding the bottom dots
        addBottomDots(0);

        // Making the notification bar transparent
        changeStatusBarColor(backgroundColors[0]);

        pagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerOnPageChangeListener);

        btnNext.setOnClickListener(v -> {
            // Checking for last page
            // If last page home screen will be launched
            int current = getItem(+1);
            if(current < layouts.length){
                // move to next screen
                viewPager.setCurrentItem(current);
            }else{
                Toast.makeText(getApplicationContext(),"END of Viewpage", Toast.LENGTH_SHORT).show();
            }
        });

        btnSkip.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Button Skip is calling now", Toast.LENGTH_SHORT).show());

    }

    private void addBottomDots(int currentPage){
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.array_dot_inactive);

        dotLayout.removeAllViews();
        for (int i = 0; i <dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[currentPage]);
            dotLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + 1;
    }

    // ViewPager Change Listeners
    ViewPager.OnPageChangeListener viewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            addBottomDots(position);

            // Changing the next button text 'NEXT' / 'GOT IT'
            if(position == layouts.length - 1){
                // Last Page make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            }else{
                // Still Pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {
            // Do Nothing
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Do nothing
        }
    };

    @SuppressLint("InlinedApi")
    private void changeStatusBarColor(String color){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * View Pager Adapter
     */

    public class MyViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
            // This is the Adapter Constructor
        }

        @NonNull
        @NotNull
        @Override
        public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}