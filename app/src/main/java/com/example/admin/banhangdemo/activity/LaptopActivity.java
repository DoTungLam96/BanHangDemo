package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.adapter.AdapterLaptop;
import com.example.admin.banhangdemo.check.Server;
import com.example.admin.banhangdemo.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    ListView listViewLaptop;
    ArrayList<SanPham> sanPhamArrayList;
    AdapterLaptop adapterLaptop;
    android.support.v7.widget.Toolbar toolbar;
    int idLaptop;
    public  int page=1;
    public boolean isLoading = false,limitData = false;
    View view;
    myHandler myHandler;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        TaoActionBar();
        getLaptop(page);
        getID();
        LoadMore();

    }

    private void LoadMore() {
        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
               if (i+i1 == i2 && i2 != 0 && isLoading == false && limitData == false){
                   isLoading = true;
                   myThread myThread = new myThread();
                   myThread.start();
               }
            }
        });

        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LaptopActivity.this,ChiTietSPActivity.class);
                intent.putExtra("sanpham",sanPhamArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getID() {
        Intent intent = getIntent();
       idLaptop = intent.getIntExtra("idLapTop",-1);
    }


    private void getLaptop(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDienThoai+String.valueOf(Page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() >2) {
                   listViewLaptop.removeFooterView(view);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("ID");
                            String ten = jsonObject.getString("tenDT");
                            int giaSP = jsonObject.getInt("giaDT");
                            String hinhAnhSP = jsonObject.getString("hinhAnhDT");
                            String moTa = jsonObject.getString("moTa");
                            int idLoaiSP = jsonObject.getInt("idLoaiSP");

                            sanPhamArrayList.add(new SanPham(id, ten, giaSP, hinhAnhSP, moTa, idLoaiSP));
                            adapterLaptop.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                  limitData = true;
                  listViewLaptop.removeFooterView(view);

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LaptopActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idloaisp",idLaptop+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        listViewLaptop = (ListView) findViewById(R.id.listViewLaptop);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolBarLaptop);
        sanPhamArrayList = new ArrayList<>();
        myHandler = new myHandler();
        adapterLaptop = new AdapterLaptop(sanPhamArrayList,R.layout.layout_adapter_laptop,getApplicationContext());
        listViewLaptop.setAdapter(adapterLaptop);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.load_layout,null);
        listViewLaptop.addFooterView(view);
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
    public class myHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0 : {
                    listViewLaptop.addFooterView(view);
                }break;
                case 1:{
                    getLaptop(++page);
                    isLoading = false;
                }break;
            }
            super.handleMessage(msg);
        }
    }
    public class myThread extends Thread{
        @Override
        public void run() {

            Message mess1 = myHandler.obtainMessage(0);
            myHandler.sendMessage(mess1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message mess2 = myHandler.obtainMessage(1);
            myHandler.sendMessage(mess2);
            super.run();
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
