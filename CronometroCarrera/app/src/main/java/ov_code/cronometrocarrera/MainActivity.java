package ov_code.cronometrocarrera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{
    TextView speedText,AvisoText;
    Chronometer cron;
    Carrera SelectedCarrera;
    Button newRaceButton,selectRaceButton;
    int status=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cron = (Chronometer)   this.findViewById(R.id.Cronometro);
        speedText = (TextView) this.findViewById(R.id.speedText);
        AvisoText = (TextView) this.findViewById(R.id.Aviso);
        newRaceButton=(Button) this.findViewById(R.id.newRace);
        selectRaceButton=(Button) this.findViewById(R.id.selectRace);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            Functions.MountLocationManager((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));
            Functions.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"GPS permission granted", Toast.LENGTH_LONG).show();
                    Functions.MountLocationManager((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));
                    Functions.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    Toast.makeText(this,"No GPS permission the aplication won´t work", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void newRace(View view){
        Intent newRace =new Intent(this,newRace.class);
        startActivity(newRace);
    }
    public void selectRace(View view){
        startActivityForResult(new Intent(this, SelectRace.class), 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                int raceNumber=data.getExtras().getInt("numero_carrera");
                SelectedCarrera=Functions.Listacarreras.get(raceNumber);
                status=0;
                AvisoText.setText("Dirigete a "+SelectedCarrera.getStart());
            }
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        int contador=0;
        if (location==null){
            contador++;
            AvisoText.setText(""+contador);
        }else{
            double speedToKm=location.getSpeed()*3.6;
            speedText.setText(String.format("%.2f", speedToKm));
            switch (status) {
                case 0:
                    if (Functions.checkDistance(location.getLatitude(), location.getLongitude(), SelectedCarrera.getStartLat(),SelectedCarrera.getStartLon()) < 0.040f) {
                        status = 1;
                        AvisoText.setText("Corre hacia la meta ubicada en " + SelectedCarrera.getEnd());
                        cron.setBase(SystemClock.elapsedRealtime());
                        cron.start();
                        newRaceButton.setEnabled(false);
                        selectRaceButton.setEnabled(false);
                        break;
                    }
                case 1:
                    if (Functions.checkDistance(location.getLatitude(), location.getLongitude(), SelectedCarrera.getEndLat(), SelectedCarrera.getEndLon()) < 0.040f) {
                        status = -1;
                        cron.stop();
                        long elapsedMillis = SystemClock.elapsedRealtime() - cron.getBase();
                        AvisoText.setText("Carrera finalizada con un tiempo de "+Functions.timeFormat(elapsedMillis));
                        newRaceButton.setEnabled(true);
                        selectRaceButton.setEnabled(true);
                        if(SelectedCarrera.getTime()<=0){
                            new DataBase(this).update(SelectedCarrera.getID(),elapsedMillis);
                        }else if (SelectedCarrera.getTime()>elapsedMillis){
                            AvisoText.setText(AvisoText.getText().toString()+"\n¡FELICIDADES! Es un nuevo record");
                        }
                        break;
                    }
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

