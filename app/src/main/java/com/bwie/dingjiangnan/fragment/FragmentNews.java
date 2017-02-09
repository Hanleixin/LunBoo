package com.bwie.dingjiangnan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bwie.dingjiangnan.R;
import com.bwie.dingjiangnan.adapter.MyBaseAdapter;
import com.bwie.dingjiangnan.adapter.Mypageradapter;
import com.bwie.dingjiangnan.bean.Bean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Dingjiangnan20170206
 * 类描述：
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 9:09
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 9:09
 * 修改备注：
 */
public class FragmentNews extends Fragment {

    private ViewPager viewPager;
    private ListView listView;
    private int distance;
    private int index;
    private String path="http://v.juhe.cn/toutiao/index?type=&key=3e6eb5d1e02ce13a6cbfc44eb41758bb";
    private ArrayList<ImageView> list=new ArrayList<>();
    //图片数组
    private String[] drawables=new String[]{
            "http://07.imgmini.eastday.com/mobile/20170206/20170206090740_0ac4cf1ea7c853412406d1b0268c4e07_1_mwpm_03200403.jpeg",
            "http://02.imgmini.eastday.com/mobile/20170206/20170206074913_f1e3c75a07f4f87864c73fa4ea93aba1_1_mwpm_03200403.jpeg",
            "http://05.imgmini.eastday.com/mobile/20170206/20170206074619_9db68bf832ea657c0402b80e15b8bb60_1_mwpm_03200403.jpeg"
    };
    private LinearLayout ll;
    //图片自动轮播的线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem+=1);
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };
    //更新数据的线程
    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Bean.ResultBean.DataBean> data= (List<Bean.ResultBean.DataBean>) msg.obj;
            //listView.setAdapter(new ArrayAdapter<Bean.ResultBean.DataBean>(getActivity(),android.R.layout.simple_list_item_1,data));
            listView.setAdapter(new MyBaseAdapter(getActivity(),data));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentnew,null);
        //获取ID
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        listView = (ListView) view.findViewById(R.id.listView);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPoint();
        viewPager.setAdapter(new Mypageradapter(getActivity(),drawables));
        viewPager.setCurrentItem(Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%drawables.length);
        handler.sendEmptyMessageDelayed(0,1000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                View childAt = ll.getChildAt(position%drawables.length);
                childAt.setEnabled(false);
                View childAt1 = ll.getChildAt(index%drawables.length);
                childAt1.setEnabled(true);
                index=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getData();
    }
    private void addPoint(){
        for(int i=0;i<drawables.length;i++){
            ImageView imageView=new ImageView(getActivity());
            imageView.setBackgroundResource(R.drawable.but_news_back);
            LinearLayoutCompat.LayoutParams layoutParams=new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=20;
            imageView.setLayoutParams(layoutParams);
            if(i==0){
                imageView.setEnabled(false);
            }
            ll.addView(imageView);
        }
    }

    public void getData() {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson=new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                List<Bean.ResultBean.DataBean> data = bean.getResult().getData();
                Message message=new Message();
                message.obj=data;
                handler1.sendMessage(message);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }
}
