package com.cqs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqs.legou_client.MainActivity;
import com.cqs.legou_client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/13.
 */
public class ContentFragment extends Fragment {
    @InjectView(R.id.vp_content)
    ViewPager mVpContent;
    @InjectView(R.id.tl_content)
    TabLayout mTlContent;
// "发现", R.mipmap.main_index_search_normal,
    String tabTitles[] = new String[]{"首页", "团购", "助手"};
    int tabImages[] = new int[]{R.mipmap.main_index_home_normal, R.mipmap.main_index_tuan_normal
            ,R.mipmap.main_index_helper_normal};
    int stabImages[] = new int[]{R.mipmap.main_index_home_pressed, R.mipmap.main_index_tuan_pressed
            , R.mipmap.main_index_search_pressed, R.mipmap.main_index_helper_pressed};

    List<Fragment> mViewList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        //获取toolbar
        MainActivity activity = (MainActivity) getActivity();
        //取消预加载
        mVpContent.setOffscreenPageLimit(1);
        mViewList = new ArrayList<>();
        mViewList.add(new HomeFragment());
        mViewList.add(new TuanFragment());
//        mViewList.add(new SearchFragment());
        mViewList.add(new HelperFragment());

        SampleFragmentPagerAdapter pagerAdapter =
                new SampleFragmentPagerAdapter(activity.getSupportFragmentManager(), this.getContext());


        mVpContent.setAdapter(pagerAdapter);
        mTlContent.setupWithViewPager(mVpContent);

        for (int i = 0; i < mTlContent.getTabCount(); i++) {
            TabLayout.Tab tab = mTlContent.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }

        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(tabTitles[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpContent.setCurrentItem(0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        @InjectView(R.id.imageView)
        ImageView mImageView;
        @InjectView(R.id.textView)
        TextView mTextView;


        public View getTabView(int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            ButterKnife.inject(this, view);
            mTextView.setText(tabTitles[position]);
            mImageView.setImageResource(tabImages[position]);
            return view;
        }

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }


}
