package com.example.eduardopalacios.bdejemplo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardopalacios.bdejemplo.Clases.Item;
import com.example.eduardopalacios.bdejemplo.Helper.BDHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    Button btnInsertar, btnMostrar;

    EditText ETPlaca,ETMarca,ETModelo,ETAnio;

    BDHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertar=findViewById(R.id.insertar);
        btnInsertar.setOnClickListener(this);

        btnMostrar=findViewById(R.id.mostrar);
        btnMostrar.setOnClickListener(this);

        ETPlaca=findViewById(R.id.placa);
        ETMarca=findViewById(R.id.marca);
        ETModelo=findViewById(R.id.modelo);
        ETAnio=findViewById(R.id.anio);

        helper=new BDHelper(this,"BDAutomoviles",null,1);
       // this.deleteDatabase("BDAutomoviles");



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.insertar:
                insertarValoes(ETPlaca.getText().toString(),ETMarca.getText().toString(),ETModelo.getText().toString(),ETAnio.getText().toString());

                break;


            case R.id.mostrar:

               recuperarValoresBD();

                break;

                default:
                    break;
        }
    }

    public void insertarValoes(String placa,String marca,String modelo, String anio)
    {
        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("placa",placa);
        contentValues.put("marca",marca);
        contentValues.put("modelo",modelo);
        contentValues.put("anio",anio);
        sqLiteDatabase.insert("Vehiculos",null,contentValues);
        sqLiteDatabase.close();
    }

    public void recuperarValoresBD()
    {
        String consulta="SELECT * FROM Vehiculos";
        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();

        Cursor cursor= sqLiteDatabase.rawQuery(consulta,null);

        List<Item> items=new ArrayList <>();


       if( cursor.moveToFirst())
       {
           items.add(new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));

           while (cursor.moveToNext())
           {

               items.add(new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));

           }
       }
       pasarValores(items);


       sqLiteDatabase.close();





    }

    public void pasarValores(List<Item> items)
    {
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("PAR", (ArrayList <? extends Parcelable>) items);
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
