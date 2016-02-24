package com.lamp.yuandaima.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lamp.yuandaima.BuildConfig;
import com.lamp.yuandaima.okhttputils.OkHttpUtils;

import org.xutils.x;

import okhttp3.OkHttpClient;

/**
 * Created by baishixin on 16/2/23.
 */
public class MyApplication extends Application {

    public static final String API = "http://api.itxdh.net/index";
    public static final String INDEX = "index";


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        /// xutils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        /// fresco
        Fresco.initialize(this);

        /// okhttp
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        // client.setConnectTimeout(100000, TimeUnit.MILLISECONDS);
    }
}
