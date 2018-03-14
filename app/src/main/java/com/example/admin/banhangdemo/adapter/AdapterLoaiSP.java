package com.example.admin.banhangdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 03/01/2018.
 */

public class AdapterLoaiSP extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiSP> spList;

    public AdapterLoaiSP(Context context, int layout, List<LoaiSP> spList) {
        this.context = context;
        this.layout = layout;
        this.spList = spList;
    }

    public int getCount() {
        return spList.size();
    }

    @Override
    public Object getItem(int i) {
        return spList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imgHinh;
        TextView txtHinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,null);
            holder.imgHinh = view.findViewById(R.id.imgLoaiSP);
            holder.txtHinh = view.findViewById(R.id.txtLoaiSP);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txtHinh.setText(spList.get(i).getTenLoaiSP());
        Picasso.with(context).load(spList.get(i).getHinhAnhLoaiSP())
                 .placeholder(R.drawable.noimage)
                 .into(holder.imgHinh);
        return view;
    }
}
