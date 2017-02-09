package com.bwie.dingjiangnan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 项目名称：Dingjiangnan20170206
 * 类描述：获取图片
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 10:56
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 10:56
 * 修改备注：
 */
public class Util {
    public static Bitmap getbitmap(String imageUri){
        Bitmap bitmap=null;
        try {
            URL url = new URL(imageUri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream is=urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
