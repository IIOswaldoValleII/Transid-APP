package com.example.transid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class typeuser extends AppCompatActivity {
    private ImageView Motorista;
    private ImageView Pasajero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeuser);

        Motorista = (ImageView) findViewById(R.id.ivMotorista);
        Pasajero = (ImageView) findViewById(R.id.ivPasajero);


        Motorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( typeuser.this, register.class);
                startActivity(intent);
            }
        });
        Pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( typeuser.this, register.class);
                startActivity(intent);
            }
        });

    }
}