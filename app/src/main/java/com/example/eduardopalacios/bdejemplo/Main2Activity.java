package com.example.eduardopalacios.bdejemplo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eduardopalacios.bdejemplo.Adaptadores.ItemAdapter;
import com.example.eduardopalacios.bdejemplo.Clases.Item;
import com.example.eduardopalacios.bdejemplo.Helper.BDHelper;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Toolbar toolbar;
    SearchView searchView;
    ItemAdapter adapter;
    List<Item> items;
    ListView listView;

    BDHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle=getIntent().getExtras();
        items=bundle.getParcelableArrayList("PAR");

        listView=findViewById(R.id.lista);

        adapter=new ItemAdapter(this,R.layout.disenio_listview,items);


        helper=new BDHelper(this,"BDAutomoviles",null,1);

        listView.setAdapter(adapter);
        onClickLista(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);

        searchView=(SearchView)searchItem.getActionView();
        searchView.setQueryHint("text");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                buscar_Elemento_operador_Like(newText);

                return false;
            }
        });
        return true;
    }

    private void onClickLista (ListView listView) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView <?> adapterView, View view, int i, long l) {
                Dialogo(i);
            }
        });
    }

    public void buscar_Elemento_operador_Like(String texto)
    {


        items=new ArrayList <>();

        String consulta="SELECT * FROM Vehiculos WHERE marca like '"+texto+"%'";

        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(consulta,null);


        if (cursor.moveToFirst())
        {

            items.add(new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));

            while (cursor.moveToNext())
          {
            items.add(new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));

          }
        }

        adapter.clear();
        adapter.addAll(items);
        listView.setAdapter(adapter);
    }

    public void Dialogo(int positionListView)
    {
        final String opciones[]={"Editar Información","Eliminar"};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Seleciona alguna opción").setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {

                if (i==0)
                {
                    eliminarRegistro();
                }
                else {

                }

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();


    }

    public void eliminarRegistro()
    {

    }

}
