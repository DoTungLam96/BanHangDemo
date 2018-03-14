package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.model.GioHang;
import com.example.admin.banhangdemo.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {

   Spinner spinner;
   TextView txtTenChiTiet,txtGiaChiTiet,txtMota;
   Button btnThem;
   Toolbar toolbar;
   ImageView imgHinhChiTiet;
    String ten;
    int id ;
    String mota;
    int gia;
    String hinhanh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        AnhXa();
        GetSanPham();
        TaoActionBar();
        getSpinner();
        ThemGioHang();
    }

    private void ThemGioHang() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (MainActivity.listGioHang.size()>0){
                   int gt1 = Integer.valueOf(spinner.getSelectedItem().toString());
                   boolean tt =false;
                   for (int i = 0 ; i < MainActivity.listGioHang.size() ; i++){
                       if (MainActivity.listGioHang.get(i).getIdSP() == id){
                          MainActivity.listGioHang.get(i).setSoLuong(MainActivity.listGioHang.get(i).getSoLuong()
                                                                      +gt1);
                          if (MainActivity.listGioHang.get(i).getSoLuong() >=10){
                              MainActivity.listGioHang.get(i).setSoLuong(10);
                          }
                          MainActivity.listGioHang.get(i).setGiaSP(gia*MainActivity.listGioHang.get(i).getSoLuong());
                          tt = true;
                       }
                   }
                   if (tt == false){
                       int sl =Integer.valueOf(spinner.getSelectedItem().toString());
                       long giamoi = sl * gia;
                       MainActivity.listGioHang.add(new GioHang(id,giamoi,ten,hinhanh,sl));
                   }

               }else {
                   int soluong =Integer.valueOf(spinner.getSelectedItem().toString() );
                   long giamoi = soluong * gia;
                   MainActivity.listGioHang.add(new GioHang(id,giamoi,ten,hinhanh,soluong));
               }
               startActivity(new Intent(ChiTietSPActivity.this,CartActivity.class));
            }
        });
    }

    private void getSpinner() {
        Integer[] mang = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mang);
        spinner.setAdapter(adapter);
    }


    private void AnhXa() {
        spinner = (Spinner) findViewById(R.id.spinner);
        txtGiaChiTiet = (TextView) findViewById(R.id.txtChiTietGia);
        txtTenChiTiet = (TextView) findViewById(R.id.txtChiTietTen);
        txtMota = (TextView) findViewById(R.id.txtChiTietMota);
        btnThem = (Button) findViewById(R.id.btnAddCart);
        toolbar = (Toolbar) findViewById(R.id.toolbarChiTiet);
        imgHinhChiTiet = (ImageView) findViewById(R.id.imgHinhChiTiet);
    }


    private void TaoActionBar() {
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
    }

    private void GetSanPham() {
        Intent intent = getIntent();
        SanPham sanPham = (SanPham) intent.getSerializableExtra("sanpham");
        ten = sanPham.getTenSP();
        id = sanPham.getId();
        mota = sanPham.getMoTa();
        gia = sanPham.getGiaSP();
        hinhanh = sanPham.getHinhAnhSP();
        Picasso.with(getApplicationContext()).load(hinhanh).into(imgHinhChiTiet);
        txtTenChiTiet.setText(ten);
        txtMota.setText(mota);
        DecimalFormat  format = new DecimalFormat("###,###");
        txtGiaChiTiet.setText("Giá: "+format.format(gia) +" VNĐ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart :
                startActivity(new Intent(this,CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
