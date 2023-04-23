package com.example.test_drone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.LineBackgroundSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button boutonConnexion;
    private EditText saisiIdentifiant;
    private EditText saisiMotDePasse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saisiIdentifiant = findViewById(R.id.identifiant);
        saisiMotDePasse = findViewById(R.id.motDePasse);
        boutonConnexion = findViewById(R.id.connexion);

        boutonConnexion.setEnabled(false);

        saisiIdentifiant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boutonConnexion.setEnabled(!s.toString().isEmpty() && !saisiMotDePasse.getText().toString().isEmpty());
            }
        });

        saisiMotDePasse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boutonConnexion.setEnabled(!s.toString().isEmpty() && !saisiIdentifiant.getText().toString().isEmpty());
            }
        });


        boutonConnexion.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Intent controleDroneActivityIntent = new Intent(MainActivity.this, ControleDroneActivity.class);

                if(saisiIdentifiant.getText().toString().equals("admin") &&
                        saisiMotDePasse.getText().toString().equals("1234")){
                    controleDroneActivityIntent.putExtra("isAdmin", true);
                }
                else{
                    controleDroneActivityIntent.putExtra("isAdmin", false);
                }
                startActivity(controleDroneActivityIntent);
            }
        });


    }

    @Override
    protected void onDestroy(){
        System.out.println("ferme1");
        super.onDestroy();
    }
}