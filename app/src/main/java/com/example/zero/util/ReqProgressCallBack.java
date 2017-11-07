package com.example.zero.util;

/**
 * Created by kazu_0122 on 2017/11/6.
 */

public interface ReqProgressCallBack<T>  extends RequestManager.ReqCallBack<T> {
    /**
     * 响应进度更新
     */
    void onProgress(long total, long current);
}