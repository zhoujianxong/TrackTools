package com.example.tracktools.maplisener;

import com.amap.api.navi.AMapNaviViewListener;

public interface AnalyticsAMapNaviViewListener extends AMapNaviViewListener {
    @Override
    default void onNaviSetting() {

    }

    @Override
    default void onNaviCancel() {

    }

    @Override
    default boolean onNaviBackClick() {
        return false;
    }

    @Override
    default void onNaviMapMode(int i) {

    }

    @Override
    default void onNaviTurnClick() {

    }

    @Override
    default void onNextRoadClick() {

    }

    @Override
    default void onScanViewButtonClick() {

    }

    @Override
    default void onLockMap(boolean b) {

    }

    @Override
    default void onNaviViewLoaded() {

    }

    @Override
    default void onMapTypeChanged(int i) {

    }

    @Override
    default void onNaviViewShowMode(int i) {

    }
}
