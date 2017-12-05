package com.ziviello.MyListViewSaveFile;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/*
----------------------------------------------------------------------------------------------------

    CLASSE PER LA GESTIONE DEI DATI DELLO STORAGE INTERNO

    DirName = Nome della cartella ( In questi metodi la cartella si trova in Documents )
    FileName = Nome del file che vogliamo creare, eliminare, ecc..
    MyObject = Oggetto che vogliamo inserire nel file

----------------------------------------------------------------------------------------------------
*/

public class Gestione_Dati_Locale {

    Context myContext;

    public Gestione_Dati_Locale(Context context)
    {
        this.myContext = context;
    }

//-- CREAZIONE DI UNA CARTELLA ( in Documents ) ----------------------------------------------------

    public File CreateDir(String DirName)
    {
        if (DirName.startsWith("/"))
        {
            File file = new File(myContext.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS), DirName);

            if (!file.mkdirs()) {
                Log.e("ATTENZIONE", "Directory non creata.");
            }
            return file;
        }
        else
        {
            File file = new File(myContext.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS),"/" + DirName);

            if (!file.mkdirs()) {
                Log.e("ATTENZIONE", "Directory non creata.");
            }
            return file;
        }
    }

//-- ELIMINAZIONE DI UNA CARTELLA ( in Documents )--------------------------------------------------

    public void DeleteDir(String DirName)
    {
        if (DirName.startsWith("/"))
        {
            File myDir = new File(myContext.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS).getAbsolutePath().toString() + DirName);
            myDir.delete();
        }
        else
        {
            File myDir = new File(myContext.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS).getAbsolutePath().toString() + "/" + DirName);
            myDir.delete();
        }
    }

//-- CREAZIONE DI UN FILE --------------------------------------------------------------------------
//-- Se il parametro DirName è una stringa vuota ( "" ) crea il file nella cartella Documents.

    public void CreateFile(String DirName, String FileName, Object MyObject, Boolean Hidden)
    {
        //  Hidden = true ----> Crea un file nascosto
        //  Hidden = false ---> Crea un file non nascosto

        FileOutputStream outputStream;

            try
            {
                outputStream = new FileOutputStream(new File(
                        CheckPath(DirName, FileName, Hidden)), true);
                ObjectOutputStream os = new ObjectOutputStream(outputStream);
                os.writeObject(MyObject);
                os.close();
                outputStream.close();
            } catch (Exception e) {
                Log.e("ATTENZIONE",
                        "CATCH - CreateFileToObject - Exception");
                e.printStackTrace();
            }
    }

//-- ELIMAZIONE DI UN FILE -------------------------------------------------------------------------
//-- Se il parametro DirName è una stringa vuota ( "" ) elimina il file nella cartella Documents

    public void DeleteFile(String DirName, String FileName, Boolean Hidden)
    {
        // Hidden = true ----> Per eliminare un file nascosto
        // Hidden = false ---> Per eliminare un file non nascosto

        File file = new File(CheckPath(DirName, FileName, Hidden));
        file.delete();
    }

//-- MODIFICA DI UN FILE ----------------------------------------------------------------------

    public void UpdateFile(String DirName, String FileName, Object MyObject, Boolean Hidden)
    {
        // Hidden = true ----> Per modificare un file oggetto nascosto
        // Hidden = false ---> Per modificare un file oggetto non nascosto

        DeleteFile(DirName, FileName, Hidden);
        CreateFile(DirName, FileName, MyObject, Hidden);
    }

//-- LETTURA DI UN FILE ----------------------------------------------------------------------------

    public Object ReadFile(String DirName, String FileName, Boolean Hidden)
    {
        Object object = new Object();

            try {
                FileInputStream fis = new FileInputStream(CheckPath(DirName, FileName, Hidden));
                ObjectInputStream ois = new ObjectInputStream(fis);

                try {
                    object = ois.readObject();
                    ois.close();
                    fis.close();

                } catch (ClassNotFoundException e) {
                    Log.e("ATTENZIONE",
                            "CATCH0 - ReadFileToObject - ClassNotFoundException");
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                Log.e("ATTENZIONE",
                        "CATCH1 - ReadFileToObject - FileNotFoundException");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("ATTENZIONE",
                        "CATCH2 - ReadFileToObject - UnsupportedEncodingException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ATTENZIONE",
                        "CATCH3 - ReadFileToObject - IOException");
                e.printStackTrace();
            }
            return object;

    }

//-- CONTROLLO ESISTENZA DI UN FILE ( CHECK ) ------------------------------------------------------

    public Boolean CheckFile(String DirName, String FileName, Boolean Hidden)
    {
        File file = new File(CheckPath(DirName, FileName, Hidden));
        if (file.exists())
        {
            Log.e("ATTENZIONE", "IL FILE " + FileName + " ESISTE.");
            return true;
        } else
            {
            Log.e("ATTENZIONE", "IL FILE " + FileName + " NON ESISTE.");
            return false;
        }
    }

//-- CONTROLLO SUL PAT -----------------------------------------------------------------------------

    public String CheckPath(String dirname, String filename, Boolean hidden) {
        String path;
        if (hidden == false)
        {
            if (filename.startsWith("."))
            {
                filename=filename.substring(1,filename.length());
            }
            else if(filename.startsWith("/."))
            {
                filename=filename.substring(2,filename.length());
            }
            if (dirname.startsWith("/")) {
                if (dirname.endsWith("/") || (filename.startsWith("/"))) {
                    if(dirname.endsWith("/") && (filename.startsWith("/")))
                    {
                        filename=filename.substring(1,filename.length());
                    }
                    path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            .getAbsolutePath() + dirname + filename;
                } else {
                    path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            .getAbsolutePath() + dirname + "/" + filename;
                }
            } else {
                if (dirname.endsWith("/") || (filename.startsWith("/"))) {
                    if(dirname.endsWith("/") && (filename.startsWith("/")))
                    {
                        filename=filename.substring(1,filename.length());
                    }
                    path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            .getAbsolutePath() + "/" + dirname + filename;
                } else {
                    path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            .getAbsolutePath() + "/" + dirname +"/" + filename;
                }
            }
        } else {
            if (filename.startsWith(".")) {
                if(filename.startsWith("./"))
                {
                    filename=filename.substring(2,filename.length());
                    filename="." + filename;
                }
                if (dirname.startsWith("/")) {
                    if (dirname.endsWith("/")) {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + dirname + filename;
                    } else {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + dirname + "/" + filename;
                    }
                } else {
                    if (!dirname.endsWith("/")) {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + "/" + dirname + "/" + filename;
                    } else {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + "/" + dirname + filename;
                    }
                }
            } else {
                if (filename.startsWith("/."))
                {
                    filename=filename.substring(2, filename.length());
                }
                else if (filename.startsWith("/"))
                {
                    filename=filename.substring(1, filename.length());
                }
                if (dirname.startsWith("/")) {
                    if (!dirname.endsWith("/")) {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + dirname + "/." + filename;
                    } else {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + dirname + "." + filename;
                    }
                } else {
                    if (!dirname.endsWith("/")) {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + "/" + dirname + "/." + filename;
                    } else {
                        path = myContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                                .getAbsolutePath() + "/" + dirname + "." + filename;
                    }
                }
            }
        }
        return path;
    }

//--------------------------------------------------------------------------------------------------

}
