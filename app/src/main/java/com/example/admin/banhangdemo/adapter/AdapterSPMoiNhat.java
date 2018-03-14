package com.example.admin.banhangdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.activity.ChiTietSPActivity;
import com.example.admin.banhangdemo.activity.DienThoaiActivity;
import com.example.admin.banhangdemo.activity.MainActivity;
import com.example.admin.banhangdemo.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 03/01/2018.
 */

public class AdapterSPMoiNhat extends RecyclerView.Adapter<AdapterSPMoiNhat.ItemViewHolder> {

    Context context;
    int layout;
    List<SanPham> sanPhamList;

    public AdapterSPMoiNhat(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(layout,null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        DecimalFormat format = new DecimalFormat("###,###");
        holder.txtGiaSP.setText("Giá : "+format.format(sanPhamList.get(position).getGiaSP()));
        holder.txtTenSP.setText(sanPhamList.get(position).getTenSP());
        Picasso.with(context).load(sanPhamList.get(position).getHinhAnhSP())
                 .placeholder(R.drawable.noimage)
                 .into(holder.imgHinhSP);
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhSP;
        TextView txtTenSP;
        TextView txtGiaSP;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgHinhSP =(ImageView) itemView.findViewById(R.id.imgHinhSP);
            txtTenSP =(TextView) itemView.findViewById(R.id.txtTenSP);
            txtGiaSP =(TextView) itemView.findViewById(R.id.txtGiaSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ChiTietSPActivity.class);
                    intent.putExtra("sanpham",sanPhamList.get(getPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }


}
