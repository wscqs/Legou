package com.cqs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqs.legou_client.R;
import com.cqs.util.MyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_content, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mIvHomeContent.setImageResource(MyUtils.navsSortImages[position]);
        holder.mTvHomeContent.setText(MyUtils.navsSort[position]);
    }


    @Override
    public int getItemCount() {
        return MyUtils.navsSort.length;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_home_content.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_home_content)
        ImageView mIvHomeContent;
        @InjectView(R.id.tv_home_content)
        TextView mTvHomeContent;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

