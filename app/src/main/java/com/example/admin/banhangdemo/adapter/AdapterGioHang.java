package com.example.admin.banhangdemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.activity.CartActivity;
import com.example.admin.banhangdemo.activity.ChiTietSPActivity;
import com.example.admin.banhangdemo.activity.MainActivity;
import com.example.admin.banhangdemo.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 06-Mar-18.
 */

public class AdapterGioHang extends BaseAdapter {
    Context context;
    int layout;
    List<GioHang> hangList;

    public AdapterGioHang(Context context, int layout, List<GioHang> hangList) {
        this.context = context;
        this.layout = layout;
        this.hangList = hangList;
    }

    public int getCount() {
        return hangList.size();
    }

    @Override
    public Object getItem(int i) {
        return hangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        ImageView imgHinh;
        TextView txtTen,txtGia,txtSoLuong;
        ImageButton btnLeft,btnRight,btnDel;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,null);
            holder.btnLeft = (ImageButton) view.findViewById(R.id.btnLeft);
            holder.btnDel = (ImageButton) view.findViewById(R.id.btnDel);
            holder.btnRight = (ImageButton) view.findViewById(R.id.btnRight);
            holder.txtTen = (TextView) view.findViewById(R.id.txtTenCartSP);
            holder.txtGia = (TextView) view.findViewById(R.id.txtGiaCartSP);
            holder.txtSoLuong = (TextView) view.findViewById(R.id.txtSoLuong);
            holder.imgHinh = (ImageView) view.findViewById(R.id.imgHinhCart);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        DecimalFormat format = new DecimalFormat("###,###");
        holder.txtGia.setText("Giá: "+format.format(hangList.get(i).getGiaSP())+" VNĐ");
        holder.txtTen.setText(hangList.get(i).getTenSP());
        holder.txtSoLuong.setText(hangList.get(i).getSoLuong()+"");
        Picasso.with(context).load(hangList.get(i).getHinhSP()).into(holder.imgHinh);
        final Animation aniL = AnimationUtils.loadAnimation(context,R.anim.left);
        final Animation aniR = AnimationUtils.loadAnimation(context,R.anim.right);
        holder.btnLeft.startAnimation(aniL);
        holder.btnRight.startAnimation(aniR);

        final int sl = Integer.parseInt(holder.txtSoLuong.getText().toString());
        if (sl>=10){
            holder.btnRight.setVisibility(View.INVISIBLE);
            holder.btnLeft.setVisibility(View.VISIBLE);
            holder.btnRight.clearAnimation();

        }else if (sl<=1){
            holder.btnRight.setVisibility(View.VISIBLE);
            holder.btnLeft.setVisibility(View.INVISIBLE);
            holder.btnLeft.clearAnimation();
        }else {
            holder.btnRight.setVisibility(View.VISIBLE);
            holder.btnLeft.setVisibility(View.VISIBLE);
            holder.btnLeft.startAnimation(aniL);
            holder.btnRight.startAnimation(aniR);
        }


        holder.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int slm = Integer.parseInt(holder.txtSoLuong.getText().toString()) -1 ;
                int slc = MainActivity.listGioHang.get(i).getSoLuong();
                long giaht =MainActivity.listGioHang.get(i).getGiaSP();
                long giamoi = (giaht*slm)/slc;
                MainActivity.listGioHang.get(i).setSoLuong(slm);
                holder.txtSoLuong.setText(slm+"");
                MainActivity.listGioHang.get(i).setGiaSP(giamoi);

                DecimalFormat format = new DecimalFormat("###,###");
                holder.txtGia.setText("Giá: "+format.format(giamoi)+" VNĐ");
                if (slm<2){
                    holder.btnLeft.setVisibility(View.INVISIBLE);
                    holder.btnRight.setVisibility(View.VISIBLE);
                    holder.btnLeft.clearAnimation();

                }else {
                    holder.btnLeft.setVisibility(View.VISIBLE);
                    holder.btnRight.setVisibility(View.VISIBLE);
                    holder.btnRight.startAnimation(aniR);
                    holder.btnLeft.startAnimation(aniL);
                }
                CartActivity.TongTien();

            }
        });
        holder.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slm = Integer.parseInt(holder.txtSoLuong.getText().toString()) +1 ;
                int slc = MainActivity.listGioHang.get(i).getSoLuong();
                long giaht = MainActivity.listGioHang.get(i).getGiaSP();
                long giamoi = (giaht*slm)/slc;
                MainActivity.listGioHang.get(i).setSoLuong(slm);
                holder.txtSoLuong.setText(slm+"");
                MainActivity.listGioHang.get(i).setGiaSP(giamoi);

                DecimalFormat format = new DecimalFormat("###,###");
                holder.txtGia.setText("Giá: "+format.format(giamoi)+" VNĐ");
                if (slm>9){
                    holder.btnLeft.setVisibility(View.VISIBLE);
                    holder.btnRight.setVisibility(View.INVISIBLE);
                    holder.btnRight.clearAnimation();

                }else {
                    holder.btnLeft.setVisibility(View.VISIBLE);
                    holder.btnRight.setVisibility(View.VISIBLE);
                    holder.btnLeft.startAnimation(aniL);
                    holder.btnRight.startAnimation(aniR);
                }
                CartActivity.TongTien();
            }

        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listGioHang.size()>0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác Nhận Xóa !");
                    builder.setIcon(R.drawable.trash);
                    builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int po) {
                            MainActivity.listGioHang.remove(i);
                            context.startActivity(new Intent(context, CartActivity.class));
                        }
                    });
                    builder.show();
                }else {

                }
            }
        });
        return view;
    }


}
