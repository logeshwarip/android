package com.example.netflix.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.DashBoard.DashBoard;
import com.example.netflix.R;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, DashBoard.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}
