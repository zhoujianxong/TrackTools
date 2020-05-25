package com.example.tracktools.maplisener;

import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.HistoryTrackResponse;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.OnTrackListener;
import com.amap.api.track.query.model.ParamErrorResponse;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackResponse;

public interface AnalyticsTrackListener extends OnTrackListener {
    @Override
    default void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

    }

    @Override
    default void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

    }

    @Override
    default void onDistanceCallback(DistanceResponse distanceResponse) {

    }

    @Override
    default void onLatestPointCallback(LatestPointResponse latestPointResponse) {

    }

    @Override
    default void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

    }

    @Override
    default void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

    }

    @Override
    default void onAddTrackCallback(AddTrackResponse addTrackResponse) {

    }

    @Override
    default void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

    }
}
