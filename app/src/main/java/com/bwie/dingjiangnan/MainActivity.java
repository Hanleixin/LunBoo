package com.bwie.dingjiangnan;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bwie.dingjiangnan.fragment.FragmentMe;
import com.bwie.dingjiangnan.fragment.FragmentNews;


/**
 * 项目名称：Dingjiangnan20170206  B卷
 * 类描述：Button+Fragment切换
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 9:09
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 9:09
 * 修改备注：
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button but_news;
    private Button but_me;
    private FragmentNews fragmentNews;
    private FragmentMe fragmentMe;
    private FragmentManager manager;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();//初始化
        ActionBar supportActionBar = getSupportActionBar();
        //将Actionbar隐藏
        if(supportActionBar!=null){
            supportActionBar.hide();
        }
        showFragment(0);
    }
    private void initview() {
        //获取ID
        but_news = (Button) findViewById(R.id.but_news);
        but_me = (Button) findViewById(R.id.but_me);
        but_news.setOnClickListener(this);
        but_me.setOnClickListener(this);
        //获得管理者
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fl,new FragmentNews()).commit();
    }
    //将按钮的背景颜色先设置为好
    private void setselect(){

        but_news.setSelected(false);
        but_me.setSelected(false);
    }
    @Override
    public void onClick(View v) {
        setselect();
        switch (v.getId()){
            case R.id.but_news:
                but_news.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentNews()).commit();
                break;
            case R.id.but_me:
                but_me.setSelected(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentMe()).commit();
                break;
        }
    }
    private void showFragment(int index){
        FragmentTransaction ft=manager.beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index){
            case 0:
                if(fragmentNews==null){
                    fragmentNews=new FragmentNews();
                    ft.add(R.id.fl,fragmentNews);
                }else{
                    ft.show(fragmentNews);
                }
                break;
            case 1:
                if(fragmentMe==null){
                    fragmentMe=new FragmentMe();
                    ft.add(R.id.fl,fragmentMe);
                }else{
                    ft.show(fragmentMe);
                }
                break;
        }

    }
    private void hideFragment(FragmentTransaction ft){
        if(fragmentNews!=null){
            ft.hide(fragmentNews);
        }else if(fragmentMe!=null){
            ft.hide(fragmentMe);
        }

    }
}
