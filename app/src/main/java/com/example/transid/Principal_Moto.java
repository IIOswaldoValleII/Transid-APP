package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Principal_Moto extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference DBBReferencia;
    private TextView nombreusuario;
    private ImageView botonmapa;
    private String nombre = "";
    private String correo = "";
    private String contraseña = "";
    private String DUI = "";
    private String Saludo = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal__moto);
        fAuth = FirebaseAuth.getInstance();
        DBBReferencia = FirebaseDatabase.getInstance().getReference();
        nombreusuario = (TextView) findViewById(R.id.txtNombreMotorista);
        botonmapa = (ImageView) findViewById(R.id.btnMapaMoto);

        botonmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Principal_Moto.this, map.class);
                startActivity(intent);
            }
        });
    }

    private void InformacionUsuario(){
        String id = fAuth.getCurrentUser().getUid();

        DBBReferencia.child("Motorista").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    nombre = snapshot.child("Nombre").getValue().toString();
                    correo = snapshot.child("Email").getValue().toString();
                    contraseña = snapshot.child("Password").getValue().toString();
                    DUI = snapshot.child("DUI").getValue().toString();
                    Saludo = "Bienvenido: " + nombre;

                    nombreusuario.setText(Saludo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        InformacionUsuario();
    }
}