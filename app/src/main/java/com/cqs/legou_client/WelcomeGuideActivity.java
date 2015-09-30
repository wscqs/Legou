package com.cqs.legou_client;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WelcomeGuideActivity extends Activity {

    @InjectView(R.id.vp_welcom_guide)
    ViewPager mVpWelcomGuide;
    @InjectView(R.id.bt_welcome_guide)
    Button mBtWelcomeGuide;
    private List<ImageView> mImageViews;
    public static final int[] mImageIds = new int[]{R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome_guide);
        ButterKnife.inject(this);
        initViewPager();

    }

    @OnClick(R.id.bt_welcome_guide)
    public void click(View v) {
        BasicUtils.sendIntent(WelcomeGuideActivity.this, MainActivity.class);
        finish();
    }

    public void initViewPager() {
        mImageViews=new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViews.add(imageView);
        }
        mVpWelcomGuide.setAdapter(new GuideAdapter());
        mVpWelcomGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mImageIds.length-1){
                    mBtWelcomeGuide.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }
    }
}
