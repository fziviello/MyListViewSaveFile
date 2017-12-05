package com.ziviello.MyListViewSaveFile;

/**
 * Created by aesys on 14/11/17.
 */

public class ElementoLista {
    private String nome;
    private String descrizione;

    public ElementoLista(String titolo) {
        this.nome = titolo;
        this.descrizione = descrizione;
    }

    public String getTitolo() {
        return nome;
    }

    public void setTitolo(String titolo) {
        this.nome = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
