package com.example.tracktools.bean;

import java.io.Serializable;

public class Data<T> extends BaseBean implements Serializable {
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
