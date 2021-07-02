package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class map extends AppCompatActivity {
    private int MY_PERMISSIONS_REQUEST_READ_CONTACT;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBBreferencia;
    private FirebaseAuth fAuth;
    private Button prueba;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fAuth = FirebaseAuth.getInstance();
        DBBreferencia = FirebaseDatabase.getInstance().getReference();
        prueba = (Button) findViewById(R.id.btnprueba);
        //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
      /*  if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            ObtenerGPS();
        }*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(map.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACT);
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {


                            Map<String, Object> map = new HashMap<>();
                            double Latitud = location.getLatitude();
                            double Longitud = location.getLongitude();

                            map.put( "Latitud", Latitud);
                            map.put("Longitud", Longitud);

                            String id = fAuth.getCurrentUser().getUid();

                            DBBreferencia.child("Pasajero_SI").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {
                                        //  Toast.makeText(map.this, "Usuario Registrado", Toast.LENGTH_SHORT).show;
                                    }else{
                                        //   Toast.makeText(map.this, "Error de registro", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });


        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( map.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        }

        /*private void ObtenerGPS(){
            LocationManager locationManager = (LocationManager) map.this.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener =  new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    String LatitudLongitud = ""+location.getLatitude()+""+location.getLongitude();
                    ubicacion.setText(LatitudLongitud);


                    Map<String, Object> map = new HashMap<>();
                    double Latitud = location.getLatitude();
                    double Longitud = location.getLongitude();

                    map.put( "Latitud", Latitud);
                    map.put("Longitud", Longitud);

                    String id = fAuth.getCurrentUser().getUid();

                    DBBreferencia.child("Pasajero_SI").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                              //  Toast.makeText(map.this, "Usuario Registrado", Toast.LENGTH_SHORT).show;
                            }else{
                             //   Toast.makeText(map.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                public void onStatusChanged(String provides, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            };
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }*/

        private void GuardarCoordenadas(){

        }
    }
