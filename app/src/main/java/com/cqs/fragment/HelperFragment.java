package com.cqs.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cqs.adapter.HelperAdapter;
import com.cqs.base.BaseFragment;
import com.cqs.entity.Helper;
import com.cqs.legou_client.R;
import com.cqs.util.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import com.marshalchen.common.ui.ToastUtil;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class HelperFragment extends BaseFragment {


    private HelperAdapter mHelperAdapter;
    private Helper mHelper;
    private ArrayList<Helper> mHelpers;
    private long oldTime;
    @InjectView(R.id.rv_helper_content)
    ListView mRvHelperContent;
    @InjectView(R.id.et_sent)
    EditText mEtSent;
    @InjectView(R.id.bt_sent)
    ImageButton mBtSent;

    @Override
    public View initView() {
        mView = View.inflate(mContext, R.layout.fragment_helper, null);
        ButterKnife.inject(this, mView);
        return mView;
    }

    @Override
    public void initData() {
        super.initData();

        //提高性能
        /*mRvHelperContent.setHasFixedSize(true);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(mContext);
        mRvHelperContent.setLayoutManager(mLinearLayout);*/
        mEtSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
                inflate.setVisibility(View.GONE);
            }
        });
        mHelpers = new ArrayList<Helper>();
        mHelperAdapter = new HelperAdapter(mContext, mHelpers);
        mRvHelperContent.setAdapter(mHelperAdapter);
        mHelper = new Helper(getRandomWelcomeTips(), false, getTime());
        mHelpers.add(mHelper);
        mBtSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
                String sent = mEtSent.getText().toString();
                mEtSent.setText("");

                if (!sent.isEmpty()) {
                    //发送的文字
                    sent = sent.replace(" ", "");
                    sent = sent.replace("\n", "");
                    mHelper = new Helper(sent, true, getTime());
                    mHelpers.add(mHelper);
                    if (mHelpers.size() > 1000) {
                        for (int i = 0; i < mHelpers.size(); i++) {
                            mHelpers.remove(i);
                        }
                    }
                    mHelperAdapter.notifyDataSetChanged();
                    //接收的文字
                    RequestParams params = new RequestParams();
                    params.add("key", Constant.TLKEY);
                    params.add("info", sent);
                    HttpUtilsAsync.get(Constant.TLBASEURL, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String responds = new String(bytes);
                            Gson gson = new Gson();
                            mHelper = null;
                            try {
                                mHelper = gson.fromJson(responds, Helper.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                            mHelper.setTime(getTime());
                            mHelpers.add(mHelper);
                            mHelper = null;
                            mHelperAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            ToastUtil.show(mContext, "服务器异常!");
                        }
                    });
                } else {
                    ToastUtil.show(mContext, "请输入文字!");
                }
            }
        });
    }

    private String getRandomWelcomeTips() {
        String welcome_tip = null;
        String[] welcome_array = getResources().getStringArray(R.array.welcome_tip);
        int index = (int) (Math.random() * (welcome_array.length - 1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }

    public String getTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >= 500) {
            oldTime = currentTime;
            return str;
        } else {
            return "";
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
