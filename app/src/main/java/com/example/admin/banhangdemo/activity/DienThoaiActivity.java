package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.adapter.AdapterDienThoai;
import com.example.admin.banhangdemo.check.CheckConnection;
import com.example.admin.banhangdemo.check.Server;
import com.example.admin.banhangdemo.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {

    ListView lvDT;
    Toolbar toolDT;
    ArrayList<SanPham> dienThoaiList;
    AdapterDienThoai adapterDienThoai;
    int idlsp;
    View footer;
    ProgressBar progressBar;
    public  int page=1;
   public boolean isLoading = false,limitData = false;
    myHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        if (CheckConnection.haveNetworkConnection(this)){
            AnhXa();
            TaoActonBar();
            getIDLoaiSP();
            getDienThoai(page);
            LoadMoreList();

        }else {
            CheckConnection.ShowConnect(this,"Kiểm tra lại kết nối Internet !");
        }
    }

    private void LoadMoreList() {

        lvDT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if ((i+i1) == i2 && i2 !=0 && isLoading == false && limitData == false){
                    isLoading = true;
                   myThread myThread = new myThread();
                   myThread.start();

                }
            }
        });

        lvDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent intent = new Intent(DienThoaiActivity.this,ChiTietSPActivity.class);
                 intent.putExtra("sanpham",dienThoaiList.get(i));
                 startActivity(intent);
            }
        });
    }

    private void getDienThoai(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDienThoai+String.valueOf(Page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() >2) {
                    lvDT.removeFooterView(footer);
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

                            dienThoaiList.add(new SanPham(id, ten, giaSP, hinhAnhSP, moTa, idLoaiSP));
                            adapterDienThoai.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData = true;
                    lvDT.removeFooterView(footer);
                  }
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DienThoaiActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idloaisp",idlsp+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getIDLoaiSP() {
        Intent intent = getIntent();
        idlsp = intent.getIntExtra("idDienThoai",-1);
    }

    private void AnhXa() {
        lvDT = (ListView) findViewById(R.id.listDienThoai);
        toolDT = (Toolbar) findViewById(R.id.toolBarDT);
        progressBar = (ProgressBar) findViewById(R.id.processBar);
        dienThoaiList = new ArrayList<>();
        myHandler = new myHandler();
        adapterDienThoai = new AdapterDienThoai(dienThoaiList,R.layout.layout_adapter_dienthoai,this);
        lvDT.setAdapter(adapterDienThoai);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.load_layout,null);

    }

    public class myHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0 : {
                    lvDT.addFooterView(footer);
                }break;
                case 1:{
                    getDienThoai(++page);
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

    private void TaoActonBar() {
        setSupportActionBar(toolDT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolDT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
