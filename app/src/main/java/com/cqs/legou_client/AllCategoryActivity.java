package com.cqs.legou_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cqs.adapter.AllCategoryAdapter;
import com.cqs.entity.Category;
import com.cqs.entity.ResponseObject;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.cqs.widget.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/22.
 */
public class AllCategoryActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.home_nav_all_categray)
    RecyclerView mHomeNavAllCategray;
    private AllCategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.home_more_all);
        ButterKnife.inject(this);
        setTitle("全部分类");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mHomeNavAllCategray.setHasFixedSize(true);
        mHomeNavAllCategray.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HttpUtilsAsync.get(Constant.GATEGORY_LIST, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes);
                Gson gson = new Gson();
                ResponseObject<List<Category>> result = gson.fromJson(data, new TypeToken<ResponseObject<List<Category>>>() {
                }.getType());
                if (1 == result.getState()) {
                    List<Category> datas = result.getDatas();
                    for(Category category:datas){
                        int position = Integer.parseInt(category.getCategoryId());
                        MyUtils.allCategrayNumber[position-1]=category.getCategoryNumber();
                    }
                    mAdapter = new AllCategoryAdapter(datas);
                    mHomeNavAllCategray.setAdapter(mAdapter);
                    mHomeNavAllCategray.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), onItemClickListener));
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
        }
    };
}