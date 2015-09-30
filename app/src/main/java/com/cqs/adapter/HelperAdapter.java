package com.cqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqs.entity.Helper;
import com.cqs.legou_client.R;
import com.marshalchen.common.ui.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/15.
 */
public class HelperAdapter extends BaseAdapter {
    @InjectView(R.id.time)
    TextView mTime;
    @InjectView(R.id.ci_helper)
    CircleImageView mCiHelper;
    @InjectView(R.id.tv_helper)
    TextView mTvHelper;

    ArrayList<Helper> mHelpers;
    private View mView;


    public HelperAdapter(Context context, ArrayList<Helper> helpers) {
        mHelpers = helpers;
    }


    @Override
    public int getCount() {
        return mHelpers.size();
    }

    @Override
    public Object getItem(int position) {
        return mHelpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (mHelpers.get(position).getIsSent()) {
            mView = inflater.inflate(R.layout.item_helper_sent, null);
        } else {
            mView = inflater.inflate(R.layout.item_helper_receive, null);
        }
        ButterKnife.inject(this,mView);
        mTime.setText(mHelpers.get(position).getTime());
        mTvHelper.setText(mHelpers.get(position).getText());
        if (0==position%2) {
            mCiHelper.setImageResource(R.mipmap.robot);
        }

        return mView;
    }



}
