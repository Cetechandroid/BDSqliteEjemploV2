package com.example.eduardopalacios.bdejemplo.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oemy9 on 21/04/2018.
 */

public class BDHelper extends SQLiteOpenHelper {

    String sqlTablaVehiculo="CREATE TABLE Vehiculos(codigo INTEGER PRIMARY KEY AUTOINCREMENT,placa TEXT, marca TEXT, modelo TEXT, anio INT )";

    public BDHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(sqlTablaVehiculo);

        ContentValues contenedor=new ContentValues();
        contenedor.put("placa","AAA111");
        contenedor.put("marca","VolksWagen");
        contenedor.put("modelo","Vochito");
        contenedor.put("anio","1936");


        sqLiteDatabase.insert("Vehiculos",null,contenedor);

    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldversion, int  newverion) {

       // sqLiteDatabase.execSQL("DROP TABLE Vehiculos");

    }
}
