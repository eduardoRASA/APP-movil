package com.example.movil.models;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.movil.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainInv extends AppCompatActivity {
    String tipo;
    /* access modifiers changed from: private */
    public boolean viewIsAtHome;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inv);
        statusBarColor();
        this.tipo = getIntent().getStringExtra("tipo");
        ((BottomNavigationView) findViewById(R.id.bottomNavigationView)).setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.depositosInvFragment:
                        selectedFragment = new DepositosFragment();
                        break;
                    case R.id.logout:
                        Intent intent = new Intent(MainInv.this, Login.class);
                        startActivity(intent);
                        break;
                    case R.id.presasInvFragment:
                        selectedFragment = new PresasFragment();
                        break;
                    case R.id.tanquesInvFragment:
                        selectedFragment = new TanquesFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selectedFragment).commit();
                return true;
            }
        });
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}