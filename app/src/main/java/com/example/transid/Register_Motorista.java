package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register_Motorista extends AppCompatActivity {

    private EditText textNombre;
    private EditText textEmail;
    private EditText textDUI;
    private EditText textPassword;
    private EditText textRuta;
    private EditText textPlaca;
    private Button btnRegistrar;
    private CheckBox Bioseguridad;

    private String nombre;
    private String email;
    private String DUI;
    private String contraseña;
    private String ruta;
    private String placa;


    FirebaseAuth fAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBBreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__motorista);
        fAuth = FirebaseAuth.getInstance();
        DBBreferencia = FirebaseDatabase.getInstance().getReference();

        textNombre = (EditText) findViewById(R.id.editTextNombreMotorista);
        textEmail = (EditText) findViewById(R.id.editTextEmailMotorista);
        textDUI = (EditText) findViewById(R.id.editTextDuiMotorista);
        textPassword = (EditText) findViewById(R.id.editTextPasswordMotorista);
        textRuta = (EditText) findViewById(R.id.editTextRutaMotorista);
        textPlaca = (EditText) findViewById(R.id.editTextPlacaMotorista);
        Bioseguridad = (CheckBox) findViewById(R.id.cbBioseguridad);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarMotorista);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = textNombre.getText().toString();
                email = textEmail.getText().toString();
                DUI = textDUI.getText().toString();
                contraseña = textPassword.getText().toString();
                ruta = textRuta.getText().toString();
                placa = textPlaca.getText().toString();

                if (!nombre.isEmpty() && !DUI.isEmpty() && !email.isEmpty() && !contraseña.isEmpty() && !ruta.isEmpty() && !placa.isEmpty()){
                    if (contraseña.length() >= 6){
                        if(Bioseguridad.isChecked()){
                            RegistrarMotorista();

                        }else{
                            RegistrarMotorista_sinBioseguridad();

                        }

                    }
                    else{
                        Toast.makeText(Register_Motorista.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Register_Motorista.this, "Porfavor, Llene los campos vacios para completar el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void RegistrarMotorista(){
        fAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = new Date();

                    String fecha = dateFormat.format(date);


                    map.put( "Nombre", nombre);
                    map.put("DUI", DUI);
                    map.put("Email", email);
                    map.put ("Password", contraseña);
                    map.put ("Ruta", ruta);
                    map.put ("Placa", placa);
                    map.put ("Bioseguridad-aplicada", "si");
                    map.put("Fecha-bioseguridad", fecha);

                    String id = fAuth.getCurrentUser().getUid();

                    DBBreferencia.child("Motorista").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(Register_Motorista.this, "Motorista Registrado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( Register_Motorista.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register_Motorista.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void RegistrarMotorista_sinBioseguridad(){
        fAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();


                    map.put( "Nombre", nombre);
                    map.put("DUI", DUI);
                    map.put( "Email", email);
                    map.put ( "Password", contraseña);
                    map.put ("Ruta", ruta);
                    map.put ("Placa", placa);
                    map.put ("Bioseguridad-aplicada", "no");


                    String id = fAuth.getCurrentUser().getUid();

                    DBBreferencia.child("Motorista").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(Register_Motorista.this, "Motorista Registrado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( Register_Motorista.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register_Motorista.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}