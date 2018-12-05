package com.example.zifang.a3dprintermate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ThirdActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
    private String printerIndex;  // for saving index of printer
    private HashMap firebaseAllData;  // for storing all data from root. If time permits find a way to retrieve from child node "3D Printer Index" only?
    private HashMap printerData;  // for storing indices of all 3D printers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.third_layout);
        NavigationView navigationView = findViewById(R.id.nav_third);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        printerIndex = intent.getStringExtra(getString(R.string.intent_key_printerIndex));

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_photo:
                tothird();
                break;
            case R.id.nav_logout:
                backtomain();
                break;
            case R.id.nav_main:
                backtosecond();
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this,"Send",Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void backtomain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.intent_key_printerIndex),printerIndex);
        startActivity(intent);
    }

    public void backtosecond(){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(getString(R.string.intent_key_printerIndex),printerIndex);
        startActivity(intent);
    }

    public void tothird(){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra(getString(R.string.intent_key_printerIndex),printerIndex);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
