package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.banhangdemo.R;
import com.example.admin.banhangdemo.check.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKyThongTinActivity extends AppCompatActivity {

    EditText txtTen,txtEmail,txtSDT,txtDiaChi;
    ImageButton btnXacNhan,btnCancel;
    String ten,diachi,email;
    String sdt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_thong_tin);
        AnhXa();
        SendInfoCus();
        SetAnimation();
    }

    private void SetAnimation() {
        Animation ani1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in);
        txtTen.startAnimation(ani1);
        Animation ani2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in2);
        txtEmail.startAnimation(ani2);
        Animation ani3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in3);
        txtSDT.startAnimation(ani3);
        Animation ani4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in4);
        txtDiaChi.startAnimation(ani4);
        Animation ani5 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in5);
        btnXacNhan.startAnimation(ani5);
        Animation ani6 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in6);
        btnCancel.startAnimation(ani6);

    }


    private void SendInfoCus() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (txtTen.getText().toString().equals("") || txtDiaChi.getText().toString().equals("") || txtEmail.getText().toString().equals("") || txtSDT.getText().toString().equals("")) {
                    Toast.makeText(DangKyThongTinActivity.this, "Mời bạn nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server.duongDanThongTinKH, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String iddh) {
                            if (iddh.length() > 0){

                                RequestQueue queue1 = Volley.newRequestQueue(getApplication());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanHoaDon, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (Integer.valueOf(response) >0 ){
                                            MainActivity.listGioHang.clear();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i< MainActivity.listGioHang.size() ; i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("TenSP",MainActivity.listGioHang.get(i).getTenSP());
                                                jsonObject.put("SoLuong",MainActivity.listGioHang.get(i).getSoLuong());
                                                jsonObject.put("GiaSP",MainActivity.listGioHang.get(i).getGiaSP());
                                                jsonObject.put("IDDH",iddh+"");
                                                jsonObject.put("IDSP",MainActivity.listGioHang.get(i).getIdSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        Map<String,String> map = new HashMap<>();
                                        map.put("json",jsonArray.toString());
                                        return map;
                                    }
                                };
                                queue1.add(stringRequest);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DangKyThongTinActivity.this, error+"", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            ten = txtTen.getText().toString();
                            sdt = txtSDT.getText().toString();
                            diachi = txtDiaChi.getText().toString();
                            email = txtEmail.getText().toString();
                            Map<String,String> map = new HashMap<>();
                            map.put("tenKH",ten);
                            map.put("email",email);
                            map.put("sdt", String.valueOf(sdt));
                            map.put("diachi",diachi);
                            return map;
                        }
                    };
                    queue.add(request);
                    startActivity(new Intent(DangKyThongTinActivity.this,MainActivity.class));
                    Toast.makeText(DangKyThongTinActivity.this, "Mua hàng thành công !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AnhXa() {
      txtTen = (EditText) findViewById(R.id.txtDangKyTen);
      txtEmail = (EditText) findViewById(R.id.txtDangKyEmail);
      txtDiaChi = (EditText) findViewById(R.id.txtDangKyDiaChi);
      txtSDT = (EditText) findViewById(R.id.txtDangKySDT);
      btnXacNhan = (ImageButton) findViewById(R.id.btnXacNhan);
      btnCancel = (ImageButton) findViewById(R.id.btnCancel);
      btnCancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(DangKyThongTinActivity.this,MainActivity.class));
          }
      });
    }
}
