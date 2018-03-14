package com.example.admin.banhangdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.admin.banhangdemo.R;

public class ThongTinLHActivity extends AppCompatActivity {

   FrameLayout frameLayout;
   TextView txtSDT;
   Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lh);
        frameLayout = findViewById(R.id.frame);
        txtSDT = findViewById(R.id.txtSDTlh);
        toolbar = findViewById(R.id.toolBarTT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0965795095"));
                startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);
        frameLayout.startAnimation(animation);
    }
}
