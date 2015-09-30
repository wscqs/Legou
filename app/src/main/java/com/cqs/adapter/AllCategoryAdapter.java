package com.cqs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqs.entity.Category;
import com.cqs.legou_client.R;
import com.cqs.util.MyUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder> {
    private List<Category> mList;
    public AllCategoryAdapter(List<Category> datas) {
        mList = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_allcategory, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mHomeNavAllItemImage.setImageResource(MyUtils.allCategrayImages[position]);
        holder.mHomeNavAllItemDesc.setText(MyUtils.allCategray[position]);
        holder.mHomeNavAllItemNumber.setText(MyUtils.allCategrayNumber[position]+"");
    }


    @Override
    public int getItemCount() {
        return MyUtils.allCategray.length;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_allcategory.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.home_nav_all_item_image)
        ImageView mHomeNavAllItemImage;
        @InjectView(R.id.home_nav_all_item_desc)
        TextView mHomeNavAllItemDesc;
        @InjectView(R.id.home_nav_all_item_number)
        TextView mHomeNavAllItemNumber;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

