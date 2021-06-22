package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private EditText textEmail;
private EditText textPassword;
private Button btnInicioSesion;
private Button btnRegistro;

private String Correo ="";
private String Password="";

private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    fAuth = FirebaseAuth.getInstance();
    textEmail =(EditText)findViewById(R.id.editText_user);
    textPassword = (EditText) findViewById(R.id.editText_password);
    btnInicioSesion = (Button) findViewById(R.id.btnInicio_sesion);
    btnRegistro = (Button) findViewById(R.id.btnRegistro);

    btnInicioSesion.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Correo = textEmail.getText().toString();
        Password = textPassword.getText().toString();

        if (!Correo.isEmpty() && !Password.isEmpty()){
            InicioLogin();
        }else{
            Toast.makeText(MainActivity.this, "Porfavor, complete los campos vacios", Toast.LENGTH_SHORT).show();
        }
    }
    });

    btnRegistro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity.this, register.class);
            startActivity(intent);
        }
    });

    }

    private void InicioLogin(){
        fAuth.signInWithEmailAndPassword(Correo, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Principal.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}