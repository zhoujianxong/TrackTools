package com.example.tracktools.maplisener;

import android.view.View;

import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

public interface AnalyticsINaviInfoCallback extends INaviInfoCallback {
    @Override
    default void onInitNaviFailure() {

    }

    @Override
    default void onGetNavigationText(String s) {

    }

    @Override
    default void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    default void onArriveDestination(boolean b) {

    }

    @Override
    default void onStartNavi(int i) {

    }

    @Override
    default void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    default void onCalculateRouteFailure(int i) {

    }

    @Override
    default void onStopSpeaking() {

    }

    @Override
    default void onReCalculateRoute(int i) {

    }

    @Override
    default void onExitPage(int i) {

    }

    @Override
    default void onStrategyChanged(int i) {

    }

    @Override
    default View getCustomNaviBottomView() {
        return null;
    }

    @Override
    default View getCustomNaviView() {
        return null;
    }

    @Override
    default void onArrivedWayPoint(int i) {

    }

    @Override
    default void onMapTypeChanged(int i) {

    }

    @Override
    default View getCustomMiddleView() {
        return null;
    }

    @Override
    default void onNaviDirectionChanged(int i) {

    }

    @Override
    default void onDayAndNightModeChanged(int i) {

    }

    @Override
    default void onBroadcastModeChanged(int i) {

    }

    @Override
    default void onScaleAutoChanged(boolean b) {

    }
}
