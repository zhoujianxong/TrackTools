package com.example.tracktools.modle.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.tracktools.MyApplication;
import com.example.tracktools.R;
import com.example.tracktools.base.BaseAdapter;
import com.example.tracktools.bean.ServerBean;
import com.example.tracktools.databinding.ItemServerHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterServerHome extends BaseAdapter<ServerBean,ItemServerHomeBinding> {
    private List<Integer> selects=new ArrayList<>();

    public AdapterServerHome(List<ServerBean> datas, View mHeaderView, View mTailView) {
        super(datas, mHeaderView, mTailView);
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_server_home;
    }

    @Override
    public void onBindingHolder(ItemServerHomeBinding binding, int position) {
        ServerBean serverBean=datas.get(position);
        binding.mItemServerHomeImage.setImageResource(R.drawable.icon_server);
        binding.mItemServerHomeName.setText(serverBean.getName());
        if (selects.contains(position)){
            binding.mItemServerHomeName.setTextColor(ContextCompat.getColor(MyApplication.context,R.color.colorAccent));
        }else {
            binding.mItemServerHomeName.setTextColor(ContextCompat.getColor(MyApplication.context,R.color.ui_gray));
        }
        setOnClickItem(position1 -> {
            if (selects.contains(position1)){
                selects.clear();
                notifyDataSetChanged();
            }else {
                selects.clear();
                selects.add(position1);
                notifyDataSetChanged();
            }
        });


    }
}
