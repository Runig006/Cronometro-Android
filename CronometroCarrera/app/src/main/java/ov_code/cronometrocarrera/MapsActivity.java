package ov_code.cronometrocarrera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    Button saveButton;
    LatLng actual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        saveButton = (Button)this.findViewById(R.id.SaveButton);

    }

    public void saveLocation(View view){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Log",actual.longitude);
        resultIntent.putExtra("Lat",actual.latitude);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Functions.MountGeocoder(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            //Point es una variable concreta de maps, indica la longitud y latitud donde has tocado
            public void onMapClick(LatLng point) {
                actual=new LatLng(point.latitude,point.longitude);
                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(point).title("Punto Seleccionado")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                saveButton.setEnabled(true);
            }
        });
    }
}

