package com.example.tracktools.maplisener;

import com.amap.api.track.OnTrackLifecycleListener;

public interface AnalyticsOnTrackLifecycleListener extends OnTrackLifecycleListener {
    @Override
    default void onBindServiceCallback(int i, String s) {

    }

    @Override
    default void onStartGatherCallback(int i, String s) {

    }

    @Override
    default void onStartTrackCallback(int i, String s) {

    }

    @Override
    default void onStopGatherCallback(int i, String s) {

    }

    @Override
    default void onStopTrackCallback(int i, String s) {

    }
}
