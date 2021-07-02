package com.example.transid;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference DBBreferencia;
    private ArrayList<Marker> MarkersTraidos = new ArrayList<>();
    private ArrayList<Marker> MarkersActualizados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        DBBreferencia = FirebaseDatabase.getInstance().getReference();
        countDownTimer();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DBBreferencia.child("Pasajero_SI").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Marker marker:MarkersActualizados){
                    marker.remove();
                }
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Coordenadas coordenadas =  snapshot1.getValue(Coordenadas.class);
                    Double Latitud = coordenadas.getLatitud();
                    Double Longitud = coordenadas.getLongitud();

                  Log.e("Coordenadas: ", + coordenadas.getLatitud() + " , " + coordenadas.getLongitud() + "" );
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(Latitud, Longitud));
                    MarkersTraidos.add(mMap.addMarker(markerOptions));
                }

                MarkersActualizados.clear();
                MarkersActualizados.addAll(MarkersTraidos);
                countDownTimer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    private void countDownTimer(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                Toast.makeText(MapsActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);
            }
        }.start();
    }

}