package com.example.test_drone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class ControleDroneActivity extends AppCompatActivity {

    private Button on;
    private Button off;
    private Button takeOff;
    private Button land;

    private Client client;

    private Boolean isAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle_drone);

        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        client = new Client("10.200.74.218",32568); //added

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!client.openConnection(isAdmin)){
                    System.out.println("echec de connexion");
                }
            }
        }).start();


        on = findViewById(R.id.on);
        off = findViewById(R.id.off);
        takeOff = findViewById(R.id.takeOff);
        land = findViewById(R.id.land);

        off.setEnabled(false);
        takeOff.setEnabled(false);
        land.setEnabled(false);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.setEnabled(false);
                off.setEnabled(true);
                takeOff.setEnabled(true);
                //client.arm();
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                off.setEnabled(false);
                on.setEnabled(true);
                takeOff.setEnabled(false);
                //client.disarm();
            }
        });

        takeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeOff.setEnabled(false);
                off.setEnabled(false);
                land.setEnabled(true);
                //client.takeoff();
            }
        });

        land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                land.setEnabled(false);
                off.setEnabled(true);
                takeOff.setEnabled(true);
                //client.land();
            }
        });


    }

    @Override
    public void onBackPressed(){
        client.closeConnection();
        super.onBackPressed();
    }
}