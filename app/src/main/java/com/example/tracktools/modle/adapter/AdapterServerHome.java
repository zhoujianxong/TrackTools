package com.example.tracktools.modle.adapter;

import android.content.Context;

import com.example.tracktools.R;
import com.example.tracktools.base.BaseAdapter;
import com.example.tracktools.bean.ServerBean;
import com.example.tracktools.databinding.ItemServerHomeBinding;

import java.util.List;

public class AdapterServerHome extends BaseAdapter<ServerBean,ItemServerHomeBinding> {

    public AdapterServerHome(List<ServerBean> datas) {
        super(datas);
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_server_home;
    }

    @Override
    public void onBindViewHolder(ItemServerHomeBinding binding, int position) {
        ServerBean serverBean=datas.get(position);
        binding.mItemServerHomeImage.setImageResource(R.drawable.setting);
        binding.mItemServerHomeName.setText(serverBean.getName());
    }
}
