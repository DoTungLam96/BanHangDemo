package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.adapter.AdapterLoaiSP;
import com.example.admin.banhangdemo.adapter.AdapterSPMoiNhat;
import com.example.admin.banhangdemo.check.CheckConnection;
import com.example.admin.banhangdemo.check.Server;
import com.example.admin.banhangdemo.model.GioHang;
import com.example.admin.banhangdemo.model.LoaiSP;
import com.example.admin.banhangdemo.model.SanPham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar;
    DrawerLayout drawerLayout;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ListView listView;
    ArrayList<LoaiSP> mangLoaiSP;
    AdapterLoaiSP adapterLoaiSP;
    ArrayList<SanPham> mangSP;
    AdapterSPMoiNhat adapterSPMoiNhat;
    public static  ArrayList<GioHang> listGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            TaoActionBar();
            ChayViewFlipper();
            GetLoaiSP();
            GetSanPham();
            GetMenu();
        }else CheckConnection.ShowConnect(getApplicationContext(),"Vui lòng kiểm tra lại Internet !");

    }

    private void GetMenu() {
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              switch (i){
                  case 0 :
                  {
                   startActivity(new Intent(MainActivity.this,MainActivity.class));
                   drawerLayout.closeDrawer(GravityCompat.START);
                  };break;
                  case 1:
                  {
                     Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                     intent.putExtra("idDienThoai",mangLoaiSP.get(i).getIdLoaiSP());
                     startActivity(intent);
                     drawerLayout.closeDrawer(GravityCompat.START);
                  }break;
                  case 2:
                  {
                      Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                      intent.putExtra("idLapTop",mangLoaiSP.get(i).getIdLoaiSP());
                      startActivity(intent);
                      drawerLayout.closeDrawer(GravityCompat.START);
                  }break;
                  case 3: {
                      startActivity(new Intent(MainActivity.this, ThongTinLHActivity.class));
                      break;
                  }
                  case 4:{
                      startActivity(new Intent(MainActivity.this,MapsActivity.class));break;
                  }
              }
          }
      });

    }

    private void GetSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Server.duongDanSPMoiNhat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i< jsonArray.length() ; i++ ){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("ID");
                        String ten = jsonObject.getString("tenSP");
                        int giaSP = jsonObject.getInt("giaSP");
                        String hinhAnhSP = jsonObject.getString("hinhAnhSP");
                        String moTa = jsonObject.getString("moTa");
                        int idLoaiSP = jsonObject.getInt("idLoaiSP");

                        mangSP.add(new SanPham(id,ten,giaSP,hinhAnhSP,moTa,idLoaiSP));
                        adapterSPMoiNhat.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void GetLoaiSP() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Server.duongDanLoaiSP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i< jsonArray.length() ; i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("ID");
                                    String tenLoaiSP = jsonObject.getString("TenLoaiSP");
                                    String hinhAnhSP = jsonObject.getString("HinhAnhLoaiSP");
                                    mangLoaiSP.add(new LoaiSP(id,tenLoaiSP,hinhAnhSP));
                                    adapterLoaiSP.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    private void ChayViewFlipper() {
        final ArrayList<String> mangQC = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Server.duongDanBanner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i< jsonArray.length() ; i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String tenHinh = jsonObject.getString("tenHinh");
                                mangQC.add(tenHinh);
                            }
                            for (int j = 0 ; j<mangQC.size() ; j++ ){
                                ImageView imageView = new ImageView(getApplicationContext());
                                Picasso.with(getApplicationContext()).load(mangQC.get(j)).into(imageView);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                viewFlipper.addView(imageView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);


        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
        viewFlipper.setInAnimation(animationIn);
        viewFlipper.setOutAnimation(animationOut);
    }


    private void TaoActionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void anhxa() {
        toolBar = (Toolbar) findViewById(R.id.toolBarMain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        navigationView = (NavigationView) findViewById(R.id.navi);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        listView = (ListView) findViewById(R.id.listViewMain);
        mangLoaiSP = new ArrayList<>();
        adapterLoaiSP = new AdapterLoaiSP(getApplicationContext(),R.layout.layout_adapter_loaisp,mangLoaiSP);
        listView.setAdapter(adapterLoaiSP);
        mangSP = new ArrayList<>();
        adapterSPMoiNhat = new AdapterSPMoiNhat(this,R.layout.layout_adapter_sanpham,mangSP);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterSPMoiNhat);
        if (listGioHang !=null){

        }else {
            listGioHang = new ArrayList<>();
        }
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
