package com.cqs.legou_client;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.LocationManagerProxy;
import com.cqs.adapter.CityAdapter;
import com.cqs.entity.City;
import com.cqs.entity.ResponseObject;
import com.cqs.fragment.HomeFragment;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.cqs.widget.RecyclerItemClickListener;
import com.cqs.widget.SideBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CityActivity extends AppCompatActivity {
    @InjectView(R.id.city_keyword)
    EditText mCityKeyword;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.side_bar)
    SideBar mSideBar;
    @InjectView(R.id.city_list)
    RecyclerView mRvCityList;

    private TextView myLocation;

    //定位
    private LocationManagerProxy mLocationManager = null;
    private CityAdapter mCityAdapter;
    private String mCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("选择城市");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        mRvCityList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvCityList.setLayoutManager(layoutManager);


        getCityData();
    }

    private void getCityData() {
        HttpUtilsAsync.get(Constant.CITY_LIST, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes);
                Gson gson = new Gson();
                ResponseObject<List<City>> result = gson.fromJson(data, new TypeToken<ResponseObject<List<City>>>() {
                }.getType());
                if (1 == result.getState()) {
                    List<City> datas = result.getDatas();
                    mCityAdapter = new CityAdapter(datas);
                    mRvCityList.setAdapter(mCityAdapter);
                    //设置监听事件
                    mRvCityList.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), onItemClickListener));


                    mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
                        @Override
                        public void onTouchingLetterChanged(String s) {
                            int index = mCityAdapter.getPositionForSection(s.toUpperCase().charAt(0));
                            if (index != -1) {
                                mRvCityList.scrollToPosition(index);
                            }
                        }
                    });
                } else {
                    MyUtils.showFalseToast(getApplicationContext());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                MyUtils.showFalseToast(getApplicationContext());
            }
        });
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener=new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            mCityName = mCityAdapter.getCityName(position);
            Snackbar.make(view, mCityName, Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra("city", mCityName);
                    setResult(MyUtils.CITY_RESULTCODE, data);
                    BasicUtils.putSharedPreferences(getApplicationContext(),Constant.FILE_NAME,"city",mCityName);
                    finish();
                }
            }).show();
        }
    };
}
