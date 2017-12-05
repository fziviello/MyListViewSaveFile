package com.ziviello.MyListViewSaveFile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by aesys on 16/11/17.
 */

public class VisualizzaActivity extends AppCompatActivity
{

    Globals g = Globals.getInstance();
    Button buttonModifica;
    EditText editNome;
    EditText editDescrizione;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza);

        buttonModifica = findViewById(R.id.buttonModifica);
        buttonModifica.setOnClickListener(PulsanteModifica);

        editNome = findViewById(R.id.editNome);
        editNome.setText(String.valueOf(g.list.get(g.getK())[0]));

        editDescrizione = findViewById(R.id.editDescrizione);
        editDescrizione.setText(String.valueOf(g.list.get(g.getK())[1]));

    }

    final View.OnClickListener PulsanteModifica = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent openScelta = new Intent(VisualizzaActivity.this, SceltaActivity.class);
            startActivity(openScelta);
        }
    };
}
