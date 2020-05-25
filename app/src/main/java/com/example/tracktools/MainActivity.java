package com.example.tracktools;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.enums.AimLessMode;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.amap.api.track.query.entity.LocationMode;
import com.amap.api.track.query.model.AddTerminalRequest;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.DistanceRequest;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.HistoryTrackResponse;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.OnTrackListener;
import com.amap.api.track.query.model.ParamErrorResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackResponse;
import com.example.tracktools.api.ConfigApi;
import com.example.tracktools.api.HttpReq;
import com.example.tracktools.api.ObserverResponse;
import com.example.tracktools.base.BaseActivity;
import com.example.tracktools.databinding.ActivityMainBinding;
import com.example.tracktools.maplisener.AnalyticsOnTrackLifecycleListener;
import com.example.tracktools.maplisener.AnalyticsTrackListener;

import java.util.Optional;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<ActivityMainBinding, Integer> {
    private String TAG = MainActivity.class.getSimpleName();
    private String TrackServer1 = "TrackServer1";
    private AMapTrackClient aMapTrackClient;
    private AMapNavi aMapNavi;

    // 这里填入你创建的服务id
    private long serviceId = 148278;
    // 唯一标识某个用户或某台设备的名称
    private String terminalName = "user1";
    //终端id
    private long terminalId = 256352251;

    @Override
    protected Integer createLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始话
        aMapTrackClient = new AMapTrackClient(getApplicationContext());
        aMapTrackClient.setInterval(5, 30);
        aMapTrackClient.setCacheSize(20);
        aMapTrackClient.setLocationMode(LocationMode.HIGHT_ACCURACY);
        aMapTrackClient.setOnTrackListener(onTrackLifecycleListener);

        initMap(savedInstanceState);
        startTrack();
        queryDistance();
    }

    private void initMap(Bundle savedInstanceState) {
        binding.mMapView.onCreate(savedInstanceState);
        aMapNavi = AMapNavi.getInstance(getApplicationContext());
        aMapNavi.startAimlessMode(AimLessMode.CAMERA_AND_SPECIALROAD_DETECTED);
        aMapNavi.startNavi(NaviType.EMULATOR);

    }

    private OnTrackLifecycleListener onTrackLifecycleListener = new AnalyticsOnTrackLifecycleListener() {
        @Override
        public void onStartGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_GATHER_SUCEE ||
                    status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
                Log.v(TAG, "定位采集开启成功 " + status + "  " + msg);

            } else {
                Toast.makeText(MainActivity.this, "定位采集启动异常，" + msg, Toast.LENGTH_SHORT).show();
                Log.v(TAG, "定位采集启动异常 " + status + "  " + msg);
            }
        }

        @Override
        public void onStartTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_TRACK_SUCEE ||
                    status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK ||
                    status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                // 服务启动成功，继续开启收集上报
                aMapTrackClient.startGather(this);
                Log.v(TAG, "继续开启收集上报 " + status + "  " + msg);
            } else {
                Log.v(TAG, "轨迹上报服务服务启动异常 " + status + "  " + msg);
            }
        }
    };

    private void queryDistance() {
        long curr = System.currentTimeMillis();
        DistanceRequest distanceRequest = new DistanceRequest(
                serviceId,
                terminalId,
                curr - 12 * 60 * 60 * 1000, // 开始时间
                curr,   // 结束时间
                -1  // 轨迹id，传-1表示包含散点在内的所有轨迹点
        );
        aMapTrackClient.queryDistance(distanceRequest, new AnalyticsTrackListener() {
            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {
                if (distanceResponse.isSuccess()) {
                    double meters = distanceResponse.getDistance();
                    // 行驶里程查询成功，行驶了meters米
                    Log.v(TAG, "查询行驶里程 成功" + meters + "米");
                    binding.mTextView.setText("已经行驶了 " + meters + " 米 ");
                    mHandler.sendEmptyMessageDelayed(what,time);
                } else {
                    // 行驶里程查询失败
                    Log.v(TAG, "查询行驶里程 失败" + distanceResponse.getErrorMsg());
                }
            }
        });
    }


    private void startTrack() {
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(serviceId, terminalName), new AnalyticsTrackListener() {
            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                terminalId = queryTerminalResponse.getTid();
                Log.v(TAG, "terminalId  = " + terminalId);
                if (queryTerminalResponse.isSuccess()) {
                    if (queryTerminalResponse.getTid() <= 0) {
                        // terminal还不存在，先创建
                        aMapTrackClient.addTerminal(new AddTerminalRequest(terminalName, serviceId), new AnalyticsTrackListener() {
                            @Override
                            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {
                                if (addTerminalResponse.isSuccess()) {
                                    // 创建完成，开启猎鹰服务
                                    terminalId = addTerminalResponse.getTid();
                                    aMapTrackClient.startTrack(new TrackParam(serviceId, terminalId), onTrackLifecycleListener);
                                } else {
                                    // 请求失败
                                    Log.v(TAG, "创建 请求失败 " + addTerminalResponse.getErrorMsg());
                                }
                            }

                            @Override
                            public void onDistanceCallback(DistanceResponse distanceResponse) {
                                if (distanceResponse != null) {
                                    binding.mTextView.setText("已经行驶了 " + distanceResponse.getDistance() + " 米 ");
                                }
                            }
                        });
                    } else {
                        // terminal已经存在，直接开启猎鹰服务
                        aMapTrackClient.startTrack(new TrackParam(serviceId, terminalId), onTrackLifecycleListener);
                    }
                } else {
                    // 请求失败
                    Log.v(TAG, "请求失败 " + queryTerminalResponse.getErrorMsg());
                }
            }

            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {
                if (distanceResponse != null) {
                    binding.mTextView.setText("已经行驶了 " + distanceResponse.getDistance() + " 米 ");
                }

            }
        });
    }


    private long time = 5 * 1000;
    public static final int what = 0x0002;
    private boolean isNotTop = true;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isNotTop) {
                queryDistance();
            }
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        binding.mMapView.onPause();
        this.isNotTop=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.isNotTop=true;
        binding.mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.isNotTop=false;
        binding.mMapView.onDestroy();
    }
}
