package com.cqs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqs.legou_client.R;

import butterknife.ButterKnife;

/**
 * Created by chenqiusong on 15/9/21.
 */
public class SearchFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = View.inflate(this.getContext(), R.layout.fragment_search, null);
        ButterKnife.inject(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
