package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class register extends AppCompatActivity {

    private EditText textNombre;
    private EditText textEmail;
    private EditText textDUI;
    private EditText textPassword;
    private Button btnRegistrar;
    private CheckBox Vacuna;



    private String nombre;
    private String email;
    private String DUI;
    private String contraseña;

    FirebaseAuth fAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBBreferencia;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        DBBreferencia = FirebaseDatabase.getInstance().getReference();
        textNombre = (EditText) findViewById(R.id.editTextNombre);
        textDUI = (EditText) findViewById(R.id.editTextDui);
        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textPassword = (EditText) findViewById(R.id.editTextPassword);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        Vacuna = (CheckBox) findViewById(R.id.cbVacuna);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            nombre = textNombre.getText().toString();
            DUI = textDUI.getText().toString();
            email = textEmail.getText().toString();
            contraseña = textPassword.getText().toString();



            if (!nombre.isEmpty() && !DUI.isEmpty() && !email.isEmpty() && !contraseña.isEmpty()){
                if (contraseña.length() >= 6){
                    if(Vacuna.isChecked()){
                        RegistrarUsuario();
                    }else{
                        RegistrarUsuario_sinvacuna();
                    }

                }
                else{
                    Toast.makeText(register.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(register.this, "Porfavor, Llene los campos vacios para completar el registro", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    private void RegistrarUsuario(){
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
                    map.put( "Email", email);
                    map.put ( "Password", contraseña);
                    map.put ("Vacuna-aplicada", "si");
                    map.put("Fecha-Vacuna", fecha);

                    String id = fAuth.getCurrentUser().getUid();

                    DBBreferencia.child("Pasajero").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(register.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(register.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                }
            });
        }

    private void RegistrarUsuario_sinvacuna(){
        fAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();

                    map.put( "Nombre", nombre);
                    map.put("DUI", DUI);
                    map.put( "Email", email);
                    map.put ( "Password", contraseña);
                    map.put ("Vacuna-aplicada", "no");

                    String id = fAuth.getCurrentUser().getUid();

                    DBBreferencia.child("Pasajero").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(register.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(register.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



    }


