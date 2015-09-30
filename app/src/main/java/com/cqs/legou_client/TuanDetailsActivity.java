package com.cqs.legou_client;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cqs.entity.Goods;
import com.cqs.entity.Shop;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chenqiusong on 15/9/23.
 */
public class TuanDetailsActivity extends AppCompatActivity {
    @InjectView(R.id.tuan_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.goods_price)
    TextView mGoodsPrice;
    @InjectView(R.id.goods_old_price)
    TextView mGoodsOldPrice;
    @InjectView(R.id.btn_buy_now)
    Button mBtnBuyNow;
    @InjectView(R.id.bottom_bar)
    LinearLayout mBottomBar;
    @InjectView(R.id.goods_image)
    ImageView mGoodsImage;
    @InjectView(R.id.click_to_big_image)
    TextView mClickToBigImage;
    @InjectView(R.id.goods_desc)
    TextView mGoodsDesc;
    @InjectView(R.id.textView1)
    TextView mTextView1;
    @InjectView(R.id.shop_title)
    TextView mShopTitle;
    @InjectView(R.id.shop_phone)
    TextView mShopPhone;
    @InjectView(R.id.shop_area)
    TextView mShopArea;
    @InjectView(R.id.imageView1)
    ImageView mImageView1;
    @InjectView(R.id.goods_title)
    TextView mGoodsTitle;
    @InjectView(R.id.tv_more_details_web_view)
    WebView mTvMoreDetailsWebView;
    @InjectView(R.id.tv_more_details)
    TextView mTvMoreDetails;
    @InjectView(R.id.wv_gn_warm_prompt)
    WebView mWvGnWarmPrompt;
    @InjectView(R.id.tv_plts)
    TextView mTvPlts;
    @InjectView(R.id.tv_more)
    TextView mTvMore;
    @InjectView(R.id.scroll_content)
    ScrollView mScrollContent;
    private Goods mGoods;
    @OnClick(R.id.shop_call)
    public void onClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+mGoods.getShop().getTel()));
        startActivity(callIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuandetail);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        setTitle("商品详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //textview加中划线
        mGoodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //让网页自适应屏幕
        WebSettings settings = mTvMoreDetailsWebView.getSettings();
        settings.setSupportZoom(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        WebSettings settings1 = mWvGnWarmPrompt.getSettings();
        settings1.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings1.setSupportZoom(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mGoods = bundle.getParcelable("goods");
        }
        if (mGoods != null) {
            initData();
        }
    }

    private void initData() {
        updateTitleImage();
        updateGoodsInfo();
        updateShopInfo();
        updateMoreDetails();
    }


    private void updateShopInfo() {
        Shop shop = mGoods.getShop();
        mShopTitle.setText(shop.getName());
        mShopPhone.setText(shop.getTel());
    }

    private void updateTitleImage() {
        Picasso.with(this).load(mGoods.getImgUrl())
                .placeholder(R.mipmap.ic_empty_dish)
                .into(mGoodsImage);
    }

    private void updateGoodsInfo() {
        mGoodsTitle.setText(mGoods.getTitle());
        mGoodsDesc.setText(mGoods.getTip());
        mGoodsPrice.setText("￥" + mGoods.getPrice());
        mGoodsOldPrice.setText("￥" + mGoods.getValue());
    }

    private void updateMoreDetails() {
        String[] data = htmlSbu(mGoods.getDetail());
        mTvMoreDetailsWebView.loadDataWithBaseURL("", data[1], "text/html", "utf-8", "");
        mWvGnWarmPrompt.loadDataWithBaseURL("",data[0],"text/html","utf-8","");
    }

    private String[] htmlSbu(String html) {
        char[] str = html.toCharArray();
        String[] data=new String[3];
        int n=0;
        int oneIndex=0;
        int secIndex=1;
        int thiIndex=2;
        for (int i = 0; i < str.length; i++) {
            if(str[i]=='【'){
                n++;
                if(1==n) oneIndex=i;
                if(2==n) secIndex=i;
                if(3==n) thiIndex=i;
            }
        }
        if(oneIndex>0&&secIndex>1&&thiIndex>2){
            data[0]=html.substring(oneIndex,secIndex);
            data[1]=html.substring(secIndex,thiIndex);
            data[2]=html.substring(thiIndex,html.length()-6);
        }
        return data;
    }
}
