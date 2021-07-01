package com.example.transid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class map extends AppCompatActivity {
    private TextView ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ubicacion = (TextView) findViewById(R.id.txtUbicacion);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            ObtenerGPS();
        }
        }

        private void ObtenerGPS(){
            LocationManager locationManager = (LocationManager) map.this.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener =  new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    String LatitudLongitud = ""+location.getLatitude()+""+location.getLongitude();
                    ubicacion.setText(LatitudLongitud);

                }
                public void onStatusChanged(String provides, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            };
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }
    }
