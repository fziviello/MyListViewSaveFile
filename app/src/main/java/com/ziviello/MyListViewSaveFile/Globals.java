package com.ziviello.MyListViewSaveFile;

import java.util.ArrayList;

public class Globals{
    private static Globals instance;
    ArrayList<String[]> list = new ArrayList<>();
    private int k;
    String[] STRING = new String[]{};
    public void setK(int d) {
        this.k = d;
    }
    public int getK() {
        return this.k;
    }
    private Globals() {
        list = new ArrayList<>();
    }

    // retrieve array from anywhere
    public ArrayList<String[]> getArray()
    {
        return this.list;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
