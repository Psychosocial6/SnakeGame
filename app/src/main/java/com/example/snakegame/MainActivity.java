package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button offline, online, run, time;
    public static MyDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
        database = new MyDB(this);
        if (database.selectAll().size() == 0) {
            for (int i = 0; i < 4; i++) {
                database.insert(0);
            }
        }
    }
    // поиск виджетов
    public void init() {
        offline = findViewById(R.id.Offline);
        online = findViewById(R.id.Online);
        run = findViewById(R.id.Run);
        time = findViewById(R.id.Time);
        offline.setOnClickListener(this);
        online.setOnClickListener(this);
        run.setOnClickListener(this);
        time.setOnClickListener(this);
    }
    // обработка нажатий
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Offline:
                Intent intent = new Intent(MainActivity.this, OfflineGame.class);
                startActivity(intent);
                break;
            case R.id.Online:
                Intent intent1 = new Intent(MainActivity.this, SecondMode.class);
                startActivity(intent1);
                break;
            case R.id.Run:
                Intent intent2 = new Intent(MainActivity.this, RunRun.class);
                startActivity(intent2);
                break;
            case R.id.Time:
                Intent intent3 = new Intent(MainActivity.this, Time.class);
                startActivity(intent3);
                break;
        }
    }
}