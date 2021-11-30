package com.example.movil.models;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movil.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash_screen);
        statusBarColor();
        new Timer().schedule(new TimerTask() {
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, Login.class));
                SplashScreen.this.finish();
            }
        }, 3000);
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
