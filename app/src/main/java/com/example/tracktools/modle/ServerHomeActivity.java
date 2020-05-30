package com.example.tracktools.modle;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.ALog;
import com.example.tracktools.R;
import com.example.tracktools.api.ObserverResponse;
import com.example.tracktools.api.ServerHttp;
import com.example.tracktools.base.BaseActivity;
import com.example.tracktools.base.BaseAdapter;
import com.example.tracktools.bean.Data;
import com.example.tracktools.bean.Result;
import com.example.tracktools.bean.ServerBean;
import com.example.tracktools.databinding.ActivityServerHomeBinding;
import com.example.tracktools.modle.adapter.AdapterServerHome;
import java.util.ArrayList;
import java.util.List;

public class ServerHomeActivity extends BaseActivity<ActivityServerHomeBinding, Integer> {

    private AdapterServerHome adapterServerHome;

    @Override
    protected Integer createLayoutId() {
        return R.layout.activity_server_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRV();

        ServerHttp.getServer().subscribe(new ObserverResponse<Result<Data<List<ServerBean>>>>(this) {
            @Override
            public void success(Result<Data<List<ServerBean>>> data) {
                adapterServerHome.addDatas(data.getData().getResults());
            }

            @Override
            public void error(Throwable e) {

            }
        });
    }

    private void initRV() {
//        LinearLayoutManager manager =new LinearLayoutManager(this);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        List<ServerBean> datas = new ArrayList<>();
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.setting);
        ImageView imageView2=new ImageView(this);
        imageView2.setImageResource(R.drawable.setting);
        adapterServerHome = new AdapterServerHome(datas,imageView,imageView2);
        binding.mRecyclerView.setLayoutManager(manager);
        binding.mRecyclerView.setAdapter(adapterServerHome);
//        for (int i = 0; i < 200; i++) {
//            datas.add(new ServerBean("描述"+i,"name"+i,"sid"+i));
//        }
//        adapterServerHome.addDatas(datas);
        adapterServerHome.setOnClickItem(position -> {
            ALog.v("单点");
        });
        adapterServerHome.setOnLongClickItem(position -> {
            ALog.v("长按");
        });
    }
}
