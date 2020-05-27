package com.example.tracktools.bean;

import androidx.annotation.NonNull;

import com.example.tracktools.utils.GsonUtil;


public class BaseBean {
    @NonNull
    @Override
    public String toString() {
        return GsonUtil.getBeanToJson(this);
    }
}
