package ov_code.cronometrocarrera;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dawmi on 25/10/2017.
 */

public final class Functions extends Application {
    public static Geocoder geocoder;
    public static LocationManager lm;
    public static ArrayList<Carrera> Listacarreras;

    static public float checkDistance(Double lat1, Double log1, Double lat2, Double log2){
        Location lo1 = new Location("");
        lo1.setLongitude(log1);
        lo1.setLatitude(lat1);
        Location lo2 = new Location("");
        lo2.setLongitude(log2);
        lo2.setLatitude(lat2);
        return lo1.distanceTo(lo2)/1000;
    }

    public static void MountGeocoder(Context con) {
        geocoder = new Geocoder(con);
    }

    public static void MountLocationManager(LocationManager lmC) {
        lm=lmC;
    }

    public static void llenarCarrera(Cursor cursor){
        Listacarreras = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                Listacarreras.add(crearCarrera(cursor));
            }while(cursor.moveToNext());
        }
    }

    protected static Carrera crearCarrera(Cursor cursor){
        int id;
        id = cursor.getInt(cursor.getColumnIndex(DataBase.ID));

        String nombre;
        nombre = cursor.getString(cursor.getColumnIndex(DataBase.NAME));

        String Start;
        Double log;
        Double lat;
        log = cursor.getDouble(cursor.getColumnIndex(DataBase.STARTLOG));
        lat = cursor.getDouble(cursor.getColumnIndex(DataBase.STARTLAT));
        Start = cursor.getString(cursor.getColumnIndex(DataBase.STARTNAME));

        String End;
        Double log2;
        Double lat2;
        log2 = cursor.getDouble(cursor.getColumnIndex(DataBase.ENDLOG));
        lat2 = cursor.getDouble(cursor.getColumnIndex(DataBase.ENDLAT));
        End = cursor.getString(cursor.getColumnIndex(DataBase.ENDNAME));

        long time = cursor.getLong(cursor.getColumnIndex((DataBase.TIME)));
        return new Carrera(id, nombre, Start, End, time, log, lat, log2, lat2);
    }
    public static String timeFormat(long time){
        int minutes = (int)(time / 1000)  / 60;
        int seconds = (int)(time / 1000) % 60;
        return minutes+":"+seconds;
    }
}
