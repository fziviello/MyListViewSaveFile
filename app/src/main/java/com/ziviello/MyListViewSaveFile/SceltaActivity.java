package com.ziviello.MyListViewSaveFile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by aesys on 13/11/17.
 */

public class SceltaActivity extends AppCompatActivity {

    Globals g = Globals.getInstance();
    EditText nome;
    EditText desc;
    TextView titolo;
    Gestione_Dati_Locale gestioneDatiLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scelta);

        Button buttonConferma = findViewById(R.id.buttonConferma);
        buttonConferma.setOnClickListener(Conferma);

        gestioneDatiLocale = new Gestione_Dati_Locale(this);

        nome = findViewById(R.id.editTextNome);
        InputFilter[] filtro = new InputFilter[1];
        filtro[0] = new InputFilter.LengthFilter(26);
        nome.setFilters(filtro);

        desc = findViewById(R.id.editTextDesc);
        titolo = findViewById(R.id.textViewTitolo);

        if(g.getK()==-1)
        /* Se K è a -1 stiamo creando una nuova riga
           le caselle di testo saranno vuote */
        {
            titolo.setText("CREA NUOVO ELEMENTO");
            buttonConferma.setText("CONFERMA");
        }
        else
        /* Se K non è a -1 stiamo modificando una riga già esistente
           nelle caselle di testo ci sarà il contenuto di quella riga */
            {
                nome.setText(String.valueOf(g.list.get(g.getK())[0]));
                desc.setText(String.valueOf(g.list.get(g.getK())[1]));
                titolo.setText("MODIFICA ELEMENTO");
                buttonConferma.setText("SALVA MODIFICHE");
            }
    }


    final View.OnClickListener Conferma = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
                if (!nome.getText().toString().equals(""))
                {
                    if(g.getK()==-1)
                    {
                    //-- CREAZIONE NUOVA RIGA --------------------------------------------------------------------

                        Intent openHome = new Intent(SceltaActivity.this, HomeActivity.class);
                        startActivity(openHome);
                        g.STRING = new String[]{nome.getText().toString(), desc.getText().toString().trim()};
                        g.list.add(g.STRING);

                        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(100);

                        if(gestioneDatiLocale.CheckFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), false)==false)
                        {
                            gestioneDatiLocale.CreateDir("/"+getResources().getString(R.string.dir_save));
                            gestioneDatiLocale.CreateFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), g.list, false);
                        }
                        else {
                            gestioneDatiLocale.UpdateFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), g.list, false);
                        }

                   } else
                       {
                       //-- MODIFICA RIGA ESISTENTE ------------------------------------------------------------------

                           Intent openHome = new Intent(SceltaActivity.this, HomeActivity.class);
                           startActivity(openHome);
                           g.STRING = new String[]{nome.getText().toString(), desc.getText().toString().trim()};
                           g.list.set(g.getK(), g.STRING);

                           Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                           vib.vibrate(100);

                          if(gestioneDatiLocale.CheckFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), false)==false)
                          {
                              gestioneDatiLocale.CreateFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), g.list, false);
                          }
                          else
                          {
                            gestioneDatiLocale.UpdateFile("/"+getResources().getString(R.string.dir_save), ""+getResources().getString(R.string.file_save), g.list, false);
                          }
                       }
                } else
                    {
                        Toast.makeText(getApplicationContext(), "NON HAI INSERITO NIENTE.", Toast.LENGTH_LONG).show();
                    }
        }
    };
}