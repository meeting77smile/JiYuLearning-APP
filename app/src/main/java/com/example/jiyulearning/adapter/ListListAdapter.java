package com.example.jiyulearning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiyulearning.R;
import com.example.jiyulearning.entity.ListInfo;
import com.example.jiyulearning.entity.ListInfo;

import java.util.List;

public class ListListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ListInfo> mListList;

    public ListListAdapter(Context context, List<ListInfo> ListInfoList) {
        this.mContext = context;
        this.mListList = ListInfoList;
    }


    @Override
    public int getCount() {
        return mListList.size();
    }

    @Override
    public Object getItem(int position) {
        return mListList.get(position).id;
    }

    @Override
    public long getItemId(int position) {
        return mListList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder =new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list,null);
            holder.tv_date_list = convertView.findViewById(R.id.tv_date_list);
            holder.tv_des = convertView.findViewById(R.id.tv_des);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ListInfo list = mListList.get(position);
        holder.tv_date_list.setText(list.date);
        holder.tv_des.setText(list.description);
        holder.tv_time.setText(String.format("%d分钟",(int)list.time));
        return convertView;
    }

    public final class ViewHolder{
        public TextView tv_date_list;
        public TextView tv_des;
        public  TextView tv_time;
    }
}
