package com.bwie.dingjiangnan.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.dingjiangnan.R;
import com.bwie.dingjiangnan.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：Dingjiangnan20170206
 * 类描述：
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 9:09
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 9:09
 * 修改备注：
 */
public class FragmentMe extends Fragment {

    private Button but_join;
    private Button but_share;
    private String AppId="222222";
    private Tencent mTencent;
    private Bitmap bitmap;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                JSONObject response= (JSONObject) msg.obj;
                if(response.has("nickname")){
                    try {
                        String nickname = response.getString("nickname");
                        me_textView.setText(nickname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else if(msg.what==1){
                Bitmap bitmap= (Bitmap) msg.obj;
                me_imageView.setImageBitmap(bitmap);

            }
        }
    };
    private ImageView me_imageView;
    private TextView me_textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentme,null);
        me_imageView = (ImageView) view.findViewById(R.id.me_imageView);
        me_textView = (TextView) view.findViewById(R.id.me_textView);
        but_join = (Button) view.findViewById(R.id.but_join);
        but_share = (Button) view.findViewById(R.id.but_share);
        mTencent = Tencent.createInstance(AppId, getActivity());
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        but_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logQQ();
            }
        });
        but_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(),"分享了！！！",Toast.LENGTH_LONG).show();
            shareToQzone();
            }
        });
    }
    private void shareToQzone(){
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
        mTencent.shareToQQ(getActivity(), params, new Base2UiListener());
    }
    private class Base2UiListener implements IUiListener{

        @Override
        public void onComplete(Object o) {
           Toast.makeText(getActivity(),"分享成功了！！！",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(),"分享失败了！！！",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(),"分享取消！！！",Toast.LENGTH_LONG).show();
        }
    }
    private void logQQ() {

        mTencent.login(getActivity(),"all",new BaseUIlistener());
    }
    private class BaseUIlistener implements IUiListener{

        @Override
        public void onComplete(Object o) {
            Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_LONG).show();
            QQToken qqToken=mTencent.getQQToken();
            UserInfo userInfo = new UserInfo(getActivity(), qqToken);
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(final Object response) {
                    Message msg=new Message();
                    msg.obj=response;
                    msg.what=0;
                    mHandler.sendMessage(msg);
                    new Thread(new Runnable() {
                        @Override
                    			public void run() {
                    				JSONObject json= (JSONObject) response;
                                    try {
                                        bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                        Message message=new Message();
                                        message.obj=bitmap;
                                        message.what=1;
                                        mHandler.sendMessage(message);
                                }
                    		}).start();
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  Base2UiListener listener=new Base2UiListener();
        if(mTencent!=null){
            mTencent.onActivityResult(requestCode,resultCode,data);
        }

    }
}
