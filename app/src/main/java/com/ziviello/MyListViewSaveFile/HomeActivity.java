package com.ziviello.MyListViewSaveFile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
{
    Globals g = Globals.getInstance(); //Instanza della classe Singleton

    ListView mylist;
    Context myContext;
    CustomAdapter adapter;
    Gestione_Dati_Locale gestioneDatiLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myContext = this;
        gestioneDatiLocale = new Gestione_Dati_Locale(myContext);

        g.setK(-1);  /* Setto K (variabile globale) a -1 per effettuare dei controlli quando entro
                        nella pagina "activity_scelta.xml"("SceltaActivity.java") */

        Button buttonScelta = findViewById(R.id.buttonAggiungi);
        buttonScelta.setOnClickListener(goToSceltaLayout);

        Button buttonSvuota = findViewById(R.id.buttonSvuota);
        buttonSvuota.setOnClickListener(Svuota);
        ListView listView = findViewById(R.id.listView);


        if(gestioneDatiLocale.CheckFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save),false)==true)
        {
            g.list = (ArrayList<String[]>) gestioneDatiLocale.ReadFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save),false);
        }
            StampaLista(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        g.setK(-1);

        ListView listView = findViewById(R.id.listView);
        gestioneDatiLocale = new Gestione_Dati_Locale(myContext);
        if(gestioneDatiLocale.CheckFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), false)==true) {
            g.list = (ArrayList<String[]>) gestioneDatiLocale.ReadFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save),false);
        }
        StampaLista(listView);
    }

//-- FUNZIONI -----------------------------------------------------------------------------------------------------------------------------------

    final View.OnClickListener Svuota = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (g.list.size() > 0)
            {
                new AlertDialog.Builder(myContext, R.style.AlertDialogCustom)
                        .setMessage("SEI SICURO DI VOLER SVUOTARE LA TUA LISTA? ( Una volta svuotata non sarà possibile recuperare nulla )")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                g.list = new ArrayList<>();
                                mylist = findViewById(R.id.listView);
                                mylist.setAdapter(null);
                                gestioneDatiLocale.UpdateFile("/"+getResources().getString(R.string.dir_save),""+getResources().getString(R.string.file_save), g.list,false);

                                Toast.makeText(myContext, "LA LISTA È STATA SVUOTATA.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            } else
                {
                    Toast.makeText(myContext, "LA TUA LISTA È GIÀ VUOTA.", Toast.LENGTH_LONG).show();
                }
        }
    };

    final View.OnClickListener goToSceltaLayout = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent openScelta = new Intent(HomeActivity.this, SceltaActivity.class);
            startActivity(openScelta);
        }
    };

    public void OnClickRow(View view)
    {
        Intent openVisualizza = new Intent(HomeActivity.this, VisualizzaActivity.class);
        startActivity(openVisualizza);
    }

    public void StampaLista(ListView listView)
    {
        final List<ElementoLista> list = new LinkedList<>();
        if(g.list!=null)
        {
            for (int i = 0; i < g.list.size(); i++)
            {
                list.add(new ElementoLista(g.list.get(i)[0]));
            }
        }
        adapter = new CustomAdapter(this, R.layout.lista_spesa, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                        new android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialogCustom)
                                .setMessage("VUOI USCIRE DALL'APPLICAZIONE?")
                                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("NO", null).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------
}
