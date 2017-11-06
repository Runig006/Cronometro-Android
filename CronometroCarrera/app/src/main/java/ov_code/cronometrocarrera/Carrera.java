package ov_code.cronometrocarrera;

import android.location.Address;

/**
 * Created by dawmi on 25/10/2017.
 */

public class Carrera {
    int ID;
    String Start;
    String End;
    String Name;
    long Time;
    Double startLon, startLat, endLon, endLat;

    public Carrera(int ID, String name, String start, String end,  long time, Double startLon, Double startLat, Double endLon, Double endLat) {
        this.ID = ID;
        Name = name;
        Start = start;
        End = end;
        Time = time;
        this.startLon = startLon;
        this.startLat = startLat;
        this.endLon = endLon;
        this.endLat = endLat;
    }

    public int getID() {
        return ID;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getName() {
        return Name;
    }

    public long getTime() {
        return Time;
    }

    public Double getStartLon() {
        return startLon;
    }

    public Double getStartLat() {
        return startLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public Double getEndLat() {
        return endLat;
    }

    public float getkm(){
        return Functions.checkDistance(startLat,startLon,endLat,endLon);
    }
}
