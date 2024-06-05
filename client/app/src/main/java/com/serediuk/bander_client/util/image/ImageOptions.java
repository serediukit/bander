package com.serediuk.bander_client.util.image;

import android.annotation.SuppressLint;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageOptions {
    @SuppressLint("CheckResult")
    public static RequestOptions imageOptions() {
        RequestOptions options = new RequestOptions();
        options
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontTransform();
        return options;
    }
}
