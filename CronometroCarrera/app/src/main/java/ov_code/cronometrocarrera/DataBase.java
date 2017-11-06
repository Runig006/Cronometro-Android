package ov_code.cronometrocarrera;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dawmi on 25/10/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME ="race";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String STARTLAT = "startLat";
    public static final String STARTLOG = "startLog";
    public static final String STARTNAME = "startname";
    public static final String ENDLAT = "endLat";
    public static final String ENDLOG = "endLog";
    public static final String ENDNAME = "endname";
    public static final String TIME = "time";

    public DataBase(Context context) {
        super(context, "DataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+TABLE_NAME+"("+
                        ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        NAME+" TEXT NOT NULL,"+
                        STARTLAT+" REAL NOT NULL,"+
                        STARTLOG+" REAL NOT NULL,"+
                        STARTNAME+" TEXT NOT NULL,"+
                        ENDLAT+" REAL NOT NULL,"+
                        ENDLOG+" REAL NOT NULL,"+
                        ENDNAME+" TEXT NOT NULL,"+
                        TIME+" REAL NOT NULL"+
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_NAME);
        db.execSQL(
                "CREATE TABLE "+TABLE_NAME+"("+
                        ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        NAME+" TEXT NOT NULL,"+
                        STARTLAT+" REAL NOT NULL,"+
                        STARTLOG+" REAL NOT NULL,"+
                        STARTNAME+" TEXT NOT NULL,"+
                        ENDLAT+" REAL NOT NULL,"+
                        ENDLOG+" REAL NOT NULL,"+
                        ENDNAME+" TEXT NOT NULL,"+
                        TIME+" REAL NOT NULL"+
                        ")"
        );
    }
    public void insert(String nameI,Double StLatI, Double StLogI,String StartNameI, Double EnLatI, Double EnLogI, String EndNameI, Float TimeI){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_NAME +"("+NAME+","+STARTLAT+","+STARTLOG+","+STARTNAME+","+ENDLAT+","+ENDLOG+","+ENDNAME+","+TIME+") VALUES("+
                    "'"+nameI+"',"+
                    StLatI+","+
                    StLogI+","+
                    "'"+StartNameI+"',"+
                    EnLatI+","+
                    EnLogI+","+
                    "'"+EndNameI+"',"+
                    TimeI+
                    ")");
    }
    public void update(int idI, Long TimeI){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+TIME+" = "+TimeI+" WHERE "+ID+" = "+idI);
    }
    public Cursor selectRaces(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    public void delete(int idI){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE ID = "+idI);
    }
}
