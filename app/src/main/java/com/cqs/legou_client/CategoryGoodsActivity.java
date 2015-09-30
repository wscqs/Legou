package com.cqs.legou_client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.cqs.adapter.TuanAdapter;
import com.cqs.entity.Goods;
import com.cqs.entity.ResponseObject;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.cqs.widget.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CategoryGoodsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.rv_tuan)
    RecyclerView mRvTuan;
    @InjectView(R.id.srl_tuan)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    private TuanAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItemPosition;
    private List<Goods> mDatas;
    private List<Goods> mLists;
    int catId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_goods);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String categoryName = getIntent().getExtras().getString("categoryName");
        catId = getIntent().getExtras().getInt("catId");
        setTitle(categoryName);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        initData();
    }

    public void initData() {
        mLists = new ArrayList<Goods>();
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshWidget.setOnRefreshListener(this);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRvTuan.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvTuan.setLayoutManager(mLayoutManager);


        mRvTuan.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = 0;
                try {
                    count = mAdapter.getItemCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == count) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    page++;
                    getDataFromNet();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeRefreshWidget.setRefreshing(false);
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        mRvTuan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mOnItemClickListener));

        getDataFromNet();
    }

    private int page = 1;
    private int size = 10;

    /**
     * 获取网路数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams();
        params.add("page", page + "");
        params.add("size", size + "");
        params.add("catId",catId+"");
        HttpUtilsAsync.post(Constant.GOODS_LIST, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String datas = new String(bytes);
                Gson gson = new Gson();
                ResponseObject<List<Goods>> result = gson.fromJson(datas, new TypeToken<ResponseObject<List<Goods>>>() {
                }.getType());
                if (1 == result.getState()) {
                    mDatas = result.getDatas();
                    mLists.addAll(mDatas);
                    if (mAdapter != null) {
                        mAdapter.addAll(mDatas);
                    } else {
                        mAdapter = new TuanAdapter(getApplicationContext());
                        mRvTuan.setAdapter(mAdapter);
                        mAdapter.addAll(mDatas);
                    }


                    new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            //解除刷新
                            mSwipeRefreshWidget.setRefreshing(false);

                            return false;
                        }
                    }).sendEmptyMessageAtTime(0, 3000);


                } else {
                    MyUtils.showFalseToast(getApplicationContext());
                    mSwipeRefreshWidget.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                MyUtils.showFalseToast(getApplicationContext());
                mSwipeRefreshWidget.setRefreshing(false);
            }
        });
    }


    private RecyclerItemClickListener.OnItemClickListener mOnItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Goods goods = mLists.get(position);
            Intent intent = new Intent(getApplicationContext(), TuanDetailsActivity.class);
            intent.putExtra("goods", goods);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        getDataFromNet();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter = null;
    }
}
