package com.ziviello.MyListViewSaveFile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

/**
 * Created by aesys on 14/11/17.
 */

    public class CustomAdapter extends ArrayAdapter<ElementoLista> {

        Globals g = Globals.getInstance();
        ArrayAdapter actualAdapter;
        Gestione_Dati_Locale gestioneDatiLocale;

        public CustomAdapter(Context context, int textViewResourceId,
                             List<ElementoLista> objects) {
            super(context, textViewResourceId, objects);
            actualAdapter = this;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lista_spesa, null);

            TextView titolo = convertView.findViewById(R.id.title);
            InputFilter[] filtro = new InputFilter[1];
            filtro[0] = new InputFilter.LengthFilter(26);
            titolo.setFilters(filtro);

            TextView descrizione = convertView.findViewById(R.id.description);

            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    finalConvertView.setBackgroundColor(getContext().getColor(R.color.colorBlue4));
                    g.setK(position);
                    Intent openVisualizza = new Intent(getContext().getApplicationContext(), VisualizzaActivity.class);
                    getContext().getApplicationContext().startActivity(openVisualizza);
                }
            });

            if(g.list.size() > 0)
            {
                Button buttonCancellaRiga = convertView.findViewById(R.id.buttonCancellaRiga);
                buttonCancellaRiga.setOnClickListener(CancellaRiga);
                buttonCancellaRiga.setId(position);
            }
            ElementoLista c = getItem(position);
            titolo.setText(c.getTitolo());
            descrizione.setText(c.getDescrizione());
            return convertView;
        }

        final View.OnClickListener CancellaRiga = new View.OnClickListener() {
            @Override
            public void onClick(final View v)  {
                new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                        .setMessage("SEI SICURO DI VOLER ELIMINARE QUESTA RIGA?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                g.list.remove(v.getId());
                                actualAdapter.remove(actualAdapter.getItem(v.getId()));
                                gestioneDatiLocale = new Gestione_Dati_Locale(getContext());
                                gestioneDatiLocale.UpdateFile("/"+getContext().getString(R.string.dir_save), ""+ getContext().getString(R.string.file_save), g.list,false);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        };

    }
