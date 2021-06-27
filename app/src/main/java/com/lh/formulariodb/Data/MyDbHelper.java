package com.lh.formulariodb.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.lh.formulariodb.Models.Ciudad;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Ciudades.db";


    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ciudad (id INTEGER PRIMARY KEY, nombre TEXT, poblacion INTEGER, latitud REAL, longitud REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists ciudad");
        onCreate(db);

    }

    public void InsertCiudad(SQLiteDatabase db, Ciudad ciudad){
        ContentValues values = new ContentValues();

        values.put("id",ciudad.getId());
        values.put("nombre",ciudad.getNombre());
        values.put("poblacion",ciudad.getPoblacion());
        values.put("latitud",ciudad.getLatitud());
        values.put("longitud",ciudad.getLongitud());

        //INSERT METODO SQLite
        db.insert("ciudad",null,values);
    }

    public ArrayList<Ciudad> seleccionCiudad (SQLiteDatabase db){
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        Cursor filasQuery = db.rawQuery("SELECT * FROM ciudad", null);
        if(filasQuery.moveToFirst()){
            do{
                Ciudad ciudad = new Ciudad(filasQuery.getInt(0),filasQuery.getString(1),filasQuery.getInt(2),filasQuery.getDouble(3),filasQuery.getDouble(4));
                ciudades.add(ciudad);
            }while(filasQuery.moveToNext());
        }
        return ciudades;
    }

    public Integer SiguienteId(SQLiteDatabase db){
        return (int) DatabaseUtils.longForQuery(db,"SELECT MAX(id)+1 FROM ciudad",null);
    }


}

