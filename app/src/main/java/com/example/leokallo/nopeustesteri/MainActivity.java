package com.example.leokallo.nopeustesteri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uusiPeli(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void suljePeli(View view) {
        super.finish();
    }

    public void naytaParhaatTulokset(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }
}
