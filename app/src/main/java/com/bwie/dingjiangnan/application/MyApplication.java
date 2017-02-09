package com.bwie.dingjiangnan.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 项目名称：Dingjiangnan20170206
 * 类描述：
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 9:31
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 9:31
 * 修改备注：
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader imageLoader=ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).build();
        imageLoader.init(configuration);
    }
}
