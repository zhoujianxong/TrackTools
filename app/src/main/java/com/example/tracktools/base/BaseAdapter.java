package com.example.tracktools.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracktools.MyApplication;

import java.util.List;

public abstract class BaseAdapter<DATA, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseAdapter.ViewHolderView> {
    private LayoutInflater layoutInflater;
    public List<DATA> datas;

    public BaseAdapter(List<DATA> datas) {
        this.layoutInflater = LayoutInflater.from(MyApplication.context);
        this.datas = datas;
    }

    public void addCleanDatas(List<DATA> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<DATA> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderView(layoutInflater.inflate(itemLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderView holder, int position) {
        onBindViewHolder((VDB) holder.binding,position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolderView<VDB extends ViewDataBinding> extends RecyclerView.ViewHolder {
        VDB binding;
        ViewHolderView(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public abstract int itemLayoutId();

    public abstract void onBindViewHolder(VDB binding, int position);

}
