package com.cqs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqs.entity.City;
import com.cqs.legou_client.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    private List<City> mList;

    public CityAdapter(List<City> datas) {
        mList = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        City city = mList.get(position);
        holder.mItemCity.setText(city.getName());
        //判断是否显示拼音的首字母
        //获取第一个字母
        int section = getSectionForPositon(position);
        if (position == getPositionForSection(section)) {
            holder.mItemCitySection.setVisibility(View.VISIBLE);
            holder.mItemCitySection.setText(city.getSortKey());
        } else {
            holder.mItemCitySection.setVisibility(View.GONE);
        }

    }

    //拼音的首字母
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mList.size(); i++) {
            char firstChar = mList.get(i).getSortKey().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    //返回首字母的值
    private int getSectionForPositon(int position) {
        return mList.get(position).getSortKey().charAt(0);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public String getCityName(int pos){
        return mList.get(pos).getName();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_city.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_city_section)
        TextView mItemCitySection;
        @InjectView(R.id.item_city)
        TextView mItemCity;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

    }
}
