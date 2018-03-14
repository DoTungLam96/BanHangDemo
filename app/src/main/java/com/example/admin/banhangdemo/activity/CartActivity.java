package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.adapter.AdapterGioHang;
import com.example.admin.banhangdemo.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ListView listCart;
   android.support.v7.widget.Toolbar toolBar;
   AdapterGioHang adapterGioHang;
  static TextView txtThongBao,txtTongTien;
   Button btnTiepTuc,btnThanhToan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        TaoActionBar();
        CheckData();
        TongTien();
        EventButton();
        XoaSP();
    }

    private void XoaSP() {

    }

    private void EventButton() {
         btnTiepTuc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(CartActivity.this,MainActivity.class));
             }
         });
         btnThanhToan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (MainActivity.listGioHang.size()>0){
                     startActivity(new Intent(CartActivity.this,DangKyThongTinActivity.class));
                 }else {
                     Toast.makeText(CartActivity.this, "Giỏ hàng đang trống mà !", Toast.LENGTH_SHORT).show();
                 }

             }
         });
    }

   public static void TongTien() {
         int tongtien = 0;
        for (int i = 0 ; i< MainActivity.listGioHang.size(); i++){
            tongtien+= MainActivity.listGioHang.get(i).getGiaSP();
        }
        DecimalFormat format = new DecimalFormat("###,###");
        txtTongTien.setText("Tổng tiền: "+format.format(tongtien)+" VNĐ");
    }

    private void CheckData() {
    if (MainActivity.listGioHang.size() <=0){
        txtThongBao.setVisibility(View.VISIBLE);
        listCart.setVisibility(View.INVISIBLE);
        adapterGioHang.notifyDataSetChanged();
    }else {
        txtThongBao.setVisibility(View.INVISIBLE);
        listCart.setVisibility(View.VISIBLE);
        adapterGioHang.notifyDataSetChanged();
    }

    }

    private void TaoActionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        txtThongBao = (TextView) findViewById(R.id.txtThongBao);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnTiepTuc = (Button) findViewById(R.id.btnTiepTuc);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        listCart = (ListView) findViewById(R.id.listViewCart);
        toolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolBarCart);
        adapterGioHang = new AdapterGioHang(this,R.layout.layout_adapter_giohang,MainActivity.listGioHang);
        listCart.setAdapter(adapterGioHang);

    }
}
