package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tracker.ConductorLoginActivity;
import com.example.tracker.FindBusActivity;
import com.example.tracker.R;

public class MainActivity extends AppCompatActivity {
    Button con,bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con=findViewById(R.id.cond);
        bus=findViewById(R.id.findbus);

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcon=new Intent(MainActivity.this, ConductorLoginActivity.class);
                startActivity(intentcon);
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbus=new Intent(MainActivity.this, FindBusActivity.class);
                startActivity(intentbus);
            }
        });
    }
}
