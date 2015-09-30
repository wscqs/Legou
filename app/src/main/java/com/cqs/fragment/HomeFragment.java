package com.cqs.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cqs.adapter.HomeAdapter;
import com.cqs.base.BaseFragment;
import com.cqs.legou_client.AllCategoryActivity;
import com.cqs.legou_client.CategoryGoodsActivity;
import com.cqs.legou_client.CityActivity;
import com.cqs.legou_client.R;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.cqs.widget.RecyclerItemClickListener;
import com.libs.zxing.CaptureActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import com.marshalchen.common.ui.ToastUtil;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class HomeFragment extends BaseFragment {

    @InjectView(R.id.tv_notice)
    TextView mTvNotice;
    @InjectView(R.id.bt_scan)
    ImageButton mBtScan;
    @InjectView(R.id.rv_home_content)
    RecyclerView mRvHomeContent;
    @InjectView(R.id.bt_city)
    Button mBtCity;
    private HomeAdapter mAdapter;


    @Override
    public View initView() {
        mView = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, mView);
        mRvHomeContent.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRvHomeContent.setLayoutManager(gridLayoutManager);
        mAdapter = new HomeAdapter();
        mRvHomeContent.setAdapter(mAdapter);
        mRvHomeContent.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mOnItemClickListener));
        return mView;
    }

    @Override
    public void initData() {
        super.initData();
        String city = BasicUtils.getSharedPreferences(getContext(), Constant.FILE_NAME, "city");
        if(!TextUtils.isEmpty(city)){
            mBtCity.setText(city);
        }

    }

    @OnClick(R.id.bt_city)
    public void onClick(View v) {
        Intent intent = new Intent(mContext, CityActivity.class);
        startActivityForResult(intent, MyUtils.CITY_REQUESTCODE);
    }

    @OnClick(R.id.bt_scan)
    public void onClickScan(View v) {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MyUtils.CITY_RESULTCODE == resultCode && MyUtils.CITY_REQUESTCODE == requestCode) {
            String city = data.getExtras().getString("city");
            mBtCity.setText(city);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private RecyclerItemClickListener.OnItemClickListener mOnItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            //全部的选项
            if (MyUtils.navsSort.length == position + 1) {
                Intent intent = new Intent(getActivity(), AllCategoryActivity.class);
                startActivity(intent);
            }else {
                int categoryId = MyUtils.categrayid[position];
                if(0!=categoryId){
                    Intent intent = new Intent(getActivity(), CategoryGoodsActivity.class);
                    intent.putExtra("categoryName",MyUtils.navsSort);
                    intent.putExtra("catId",categoryId);
                    startActivity(intent);
                }else {
                    ToastUtil.show(getContext(),"此分类没有商品,敬请期待");
                }

            }

        }
    };
}
