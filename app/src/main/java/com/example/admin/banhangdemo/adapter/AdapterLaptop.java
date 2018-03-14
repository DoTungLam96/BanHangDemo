package com.example.admin.banhangdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 06-Mar-18.
 */

public class AdapterLaptop extends BaseAdapter {
    List<SanPham> list;
    int layout;
    Context context;

    public AdapterLaptop(List<SanPham> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup) {
        ItemViewHolder holder;
        if (itemView == null) {
            holder = new ItemViewHolder();
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, null);
            holder.imgLaptop = itemView.findViewById(R.id.imgLaptop);
            holder.txtTenLT = itemView.findViewById(R.id.txtTenLT);
            holder.txtGiaLT = itemView.findViewById(R.id.txtGiaLT);
            holder.txtMotaLT = itemView.findViewById(R.id.txtMoTaLT);
            itemView.setTag(holder);
        }else {
            holder = (ItemViewHolder) itemView.getTag();
        }
        holder.txtMotaLT.setMaxLines(2);
        holder.txtMotaLT.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtMotaLT.setText(list.get(position).getMoTa());
        holder.txtTenLT.setText(list.get(position).getTenSP());
        DecimalFormat format = new DecimalFormat("###,###");
        holder.txtGiaLT.setText("Giá: "+format.format(list.get(position).getGiaSP()));
        Picasso.with(context).load(list.get(position).getHinhAnhSP())
                .placeholder(R.drawable.noimage)
                .into(holder.imgLaptop);
        return itemView;
    }

    class ItemViewHolder{
        ImageView imgLaptop;
        TextView txtTenLT;
        TextView txtGiaLT;
        TextView txtMotaLT;
    }
}
