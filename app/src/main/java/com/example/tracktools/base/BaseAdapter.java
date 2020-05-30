package com.example.tracktools.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracktools.MyApplication;

import java.util.List;

public abstract class BaseAdapter<DATA, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseAdapter.VH> {
    public List<DATA> datas;
    private OnClickItem onClickItem;
    private OnLongClickItem onLongClickItem;
    private View mHeaderView, mTailView;
    public static final int TYPE_HEADER = -1;//headView
    public static final int TYPE_TAIL = -2;//buttonView

    public BaseAdapter(List<DATA> datas,View mHeaderView, View mTailView){
        this.datas=datas;
        this.mHeaderView=mHeaderView;
        this.mTailView=mTailView;
        if (this.mHeaderView!=null){
            this.datas.add(null);
        }
        if (this.mTailView!=null){
            this.datas.add(null);
        }
    }

    public void addCleanDatas(List<DATA> datas) {
        this.datas.clear();
        if (mHeaderView!=null){
            datas.add(null);
        }
        this.datas.addAll(datas);
        if (mTailView!=null){
            this.datas.add(null);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<DATA> datas) {
        if (mTailView!=null){
            this.datas.addAll(datas.size()-1,datas);
        }else {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView!=null){
            if (position==0){
                return TYPE_HEADER;
            }
        }
        if (mTailView !=null){
            if (position==getItemCount()-1){
                return TYPE_TAIL;
            }
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //如果设置了headView  则返回头部
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new VH(mHeaderView);
        }
        //设置了tailView  则返回尾部
        if (mTailView!=null&&viewType== TYPE_TAIL){
            return new VH(mTailView);
        }
        return new VH(LayoutInflater.from(MyApplication.context).inflate(itemLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (null != onClickItem) {
                onClickItem.onClickItem(position);
            }
        });
        if (mHeaderView!=null){
            if (position==0){
                //headView显示头部信息 不处理
                return;
            }
        }
        if (mTailView!=null){
            if (position==getItemCount()-1){
                //buttonView底部信息
                return;
            }
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (null != onLongClickItem) {
                onLongClickItem.onLongClickItem(position);
            }
            return true;
        });
        VDB binding = DataBindingUtil.bind(holder.itemView);
        onBindingHolder(binding, position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public abstract int itemLayoutId();

    public abstract void onBindingHolder(VDB binding, int position);


    public interface OnClickItem {
        public void onClickItem(int position);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnLongClickItem {
        public void onLongClickItem(int position);
    }

    public void setOnLongClickItem(OnLongClickItem onLongClickItem) {
        this.onLongClickItem = onLongClickItem;
    }

}
