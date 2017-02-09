package com.bwie.dingjiangnan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.dingjiangnan.R;
import com.bwie.dingjiangnan.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Dingjiangnan20170206
 * 类描述：
 * 创建人：${丁江楠}
 * 创建时间：2017/2/6 10:29
 * 修改人：${丁江楠}
 * 修改时间：2017/2/6 10:29
 * 修改备注：
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context context;
    private List<Bean.ResultBean.DataBean> list=new ArrayList<>();

    public MyBaseAdapter(Context context, List<Bean.ResultBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHoldr viewHoldr=null;
        if(view==null){
            view=View.inflate(context, R.layout.item_list,null);
            viewHoldr=new ViewHoldr();
            viewHoldr.imageView= (ImageView) view.findViewById(R.id.item_imageView);
            viewHoldr.textView1= (TextView) view.findViewById(R.id.item_title);
            viewHoldr.textView2 = (TextView) view.findViewById(R.id.item_intro);
            view.setTag(viewHoldr);
        }else{
            viewHoldr = (ViewHoldr) view.getTag();
        }
        Bean.ResultBean.DataBean dataBean = list.get(position);
        ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s(),viewHoldr.imageView);
        viewHoldr.textView1.setText(dataBean.getTitle());
        viewHoldr.textView2.setText(dataBean.getAuthor_name());
        return view;
    }
    class ViewHoldr{
        TextView textView1,textView2;
        ImageView imageView;
    }
}
