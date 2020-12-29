package sa.videolinks;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import sa.videolinks.DatabaseFiles.FilmDatabase;
import sa.videolinks.Utility.AlertDialogs;
import sa.videolinks.Utility.CopyOnClipboard;
import sa.videolinks.Utility.DataEora;
import sa.videolinks.Utility.JsonDataConverter;
import sa.videolinks.Utility.JsonObject;

public class VideoLinksActivity extends AppCompatActivity {

    public static File F ;
    public static String DBFilePath;
    public static String DBFile;
    public static String jsonPath;
    public static ArrayList<String> items = new ArrayList<String>();
    /* Permessi per la lettura e scrittura dei file */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static FilmDatabase db;
    public static String[] contenutoDB = null;
    public static JsonObject[] jsonObjectArr = null; // Oggetto contenente tutti i dati di tutti i film

    public static int filmClicked = 0;

    ImageButton addItem;
    ImageButton DBCheck;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_links);

        DBFilePath = Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/DB/";
        jsonPath = Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/Config/";
        DBFile = Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/DB/FilmDatabase.db";
        File DB = new File(DBFilePath);
        boolean fileSuccess = fileCreator(DB, DBFile);
        new File(jsonPath).mkdir(); // Creo cartella di configurazione

        addItem = (ImageButton) findViewById(R.id.imageButton2);
        DBCheck = (ImageButton) findViewById(R.id.imageButton4);

        try {
            db = new FilmDatabase(getApplicationContext());
            Cursor res = db.getAllData();

            if(res.getCount() == 0) {
                // show message
                Toast.makeText(this.getApplicationContext(), "Il database non contiene dati",Toast.LENGTH_SHORT).show();
                AlertDialogs.createAlertDialogToShowDBDataMiss(
                        VideoLinksActivity.this,
                        "yes",
                        "no",
                        "Il database non contiene dati. Vuoi popolarlo con le info di un file esterno?",
                        "Attenzione");
            }

        } catch(Exception e) {
            System.out.println("Errore: " + e);
        }

        listView = (ListView) findViewById(R.id.listView);
        String [] array = {"FILM: Antonio","FILM: Giovanni","FILM: Michele","FILM: Giuseppe", "FILM: Leonardo",
                "FILM: Alessandro", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test", "FILM: test",
                "FILM: test", "FILM: test"};

        Cursor res = db.getAllData();
        if(res.getCount() == 0) {
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this, R.layout.film_list, R.id.textView7, array);
            listView.setAdapter(arrayAdapter);
        } else {
            String contenutoDatabase [] = getCursorContent(res);
            JsonObject[] getJsonObjectArr = jsonObjectArr = JsonObject.getJsonObjectArr(contenutoDatabase);
            String nomiFilm[] = getNomiFilmFromJsonObject(getJsonObjectArr);
            System.out.println("viao");
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this, R.layout.film_list, R.id.textView7, nomiFilm);
            listView.setAdapter(arrayAdapter);
        }



        /* LISTENERS */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filmClicked = position;

                // Mostro il link del film cliccato
                Toast.makeText(
                        getApplicationContext(),
                        jsonObjectArr[position].NomeFilm + "\n" + jsonObjectArr[position].UrlFilm,
                        Toast.LENGTH_SHORT).show();

                // Copio il link sulla clipboard
                CopyOnClipboard clipboard = new CopyOnClipboard();
                clipboard.setClipboard(getApplicationContext(), jsonObjectArr[position].UrlFilm);

                // Lancio una AlertDialog che propone di vedere il video sul telefono oppure in miracast
                new AlertDialogs().createAlertDialogToChooseViewType(VideoLinksActivity.this, "Sulla TV", "Sul telefono", "Dove vuoi visualizzare il video?", "Video");

                //Intent i = new Intent(VideoLinksActivity.this, VideoLinksActivity.class);
                //startActivity(i);
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        DBCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"***TODO***\nRicerca del file DB nella memoria dello smartphone",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Ottiene un array di stringhe contenenti i nomi di tutti i film
     * @param getJsonObjectArr Oggetto contenente i dati di tutti i film
     * @return array di nomi
     */
    public String[] getNomiFilmFromJsonObject(JsonObject[] getJsonObjectArr){
        ArrayList <String> nomi = new ArrayList<String>();
        try {
            for (JsonObject nome : getJsonObjectArr) {
                nomi.add(nome.NomeFilm);
            }
        }  catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return nomi.toArray(new String[nomi.size()]);
    }

    public String[] getCursorContent (Cursor c) {
        ArrayList<String> nomiFilm = null;
        try {
            nomiFilm = new ArrayList<String>();
            int cont = 0;
            while (c.moveToNext()) {
                nomiFilm.add(c.getString(0)); // ID
                nomiFilm.add(c.getString(1)); // Nome film
                nomiFilm.add(c.getString(2)); // URL
            }
            return nomiFilm.toArray(new String[nomiFilm.size()]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean jsonFileExists() {
        String path  = jsonPath +"JsonDatabase.json";
        File jsonFile = new File(path);
        return jsonFile.exists();
    }

    public void getJsonDataAndFillDatabase() {
        String jsonData = getJsonData();
        riempiDatabase(jsonData);
    }

    public void riempiDatabase(String jsonData) {
        try {
            JsonDataConverter jsonDataConverter = new JsonDataConverter();
            String [] NomeFilm = jsonDataConverter.parse(jsonData, "NomeFilm");
            String [] UrlFilm = jsonDataConverter.parse(jsonData, "UrlFilm");
            JsonObject[] jsonObjectArr = JsonObject.getJsonObjectArr(NomeFilm, UrlFilm);
            DataEora d = new DataEora();
            String dataEora = d.getData() + " " + d.getOra();
            for(JsonObject jsonObject : jsonObjectArr){
                boolean success = db.insertData(
                        jsonObject.NomeFilm,
                        jsonObject.UrlFilm,
                        jsonObject.NomeFilm,
                        dataEora
                );
                System.out.println("success: " + success);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getJsonData() {
        String path  = jsonPath +"JsonDatabase.json";
        File jsonFile = new File(path);
        String jsonText = null;
        try {
            Scanner in = new Scanner(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            while(in.hasNext()) {
                sb.append(in.next());
            }
            in.close();
            jsonText = sb.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return jsonText;
    }

    public void listInsert(ListView listView) {
        /* TODO */
    }


    /**
     * Metodo che crea la directory dell'oggetto file passato come parametro e genera al suo interno
     * un file; se non esiste e ritorna un booleno
     * @param f Oggetto File contenente il path per la creazione della cartella
     * * @param DBFile Stringa per il path in cui creare il file
     * @return
     */
    public boolean fileCreator(File f, String DBFile)  {
        boolean b = f.exists();
        if (!b) {
            f = new File(DBFilePath);
            verifyStoragePermissions(this);
            try {
                f.mkdirs();
                b = new File(DBFile).createNewFile();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "File missing: a new file will be now created", Toast.LENGTH_SHORT).show();
            }
        }
        if(b && !new File(DBFile).exists()) {
            try {
                b = new File(DBFile).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }




}
