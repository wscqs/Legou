package com.cqs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqs.entity.Goods;
import com.cqs.legou_client.R;
import com.cqs.util.MyUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class TuanAdapter extends RecyclerView.Adapter<TuanAdapter.MyViewHolder> {
    private  Context mContext;
    private List<Goods> mList=new ArrayList<Goods>();

    public TuanAdapter(List<Goods> datas, Context context) {
        mList = datas;
        mContext=context;
    }

    public TuanAdapter(Context context) {
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tuan_goods, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mIndexGlItemTitle.setText(mList.get(position).getTitle());
        holder.mIndexGlItemTitlecontent.setText(mList.get(position).getSortTitle());
        holder.mIndexGlItemPrice.setText(mList.get(position).getPrice());
        //价值
        StringBuffer sbf = new StringBuffer("￥" + mList.get(position).getValue());
        //加下划线
        SpannableString spannableString=new SpannableString(sbf);
        spannableString.setSpan(new StrikethroughSpan(),0,sbf.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.mIndexGlItemValue.setText(spannableString);
        holder.mIndexGlItemCount.setText(mList.get(position).getBought()+"份");
        Picasso.with(mContext).load(mList.get(position).getImgUrl())
                .placeholder(R.mipmap.ic_empty_dish).into(holder.mIndexGlItemImage);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void addAll(List<Goods> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }
    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_tuan_goods.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class MyViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.index_gl_item_image)
        ImageView mIndexGlItemImage;
        @InjectView(R.id.index_gl_item_title)
        TextView mIndexGlItemTitle;
        @InjectView(R.id.index_gl_item_titlecontent)
        TextView mIndexGlItemTitlecontent;
        @InjectView(R.id.index_gl_item_price)
        TextView mIndexGlItemPrice;
        @InjectView(R.id.index_gl_item_value)
        TextView mIndexGlItemValue;
        @InjectView(R.id.index_gl_item_count)
        TextView mIndexGlItemCount;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

