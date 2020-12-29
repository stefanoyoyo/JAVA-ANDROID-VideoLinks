package sa.videolinks.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by ament on 05/12/2018.
 */

    public class FilmDatabase extends SQLiteOpenHelper {

    Context context;
    private String database_name = "FilmDatabase.db";
    private String table_name = "FilmTable";
    private String visibleDbPath = Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/DB/FilmDatabase.db";

    /*
    public FilmDatabase(Context context) {
            super(context, "FilmDatabase.db", null, 1);
        }
        */
    public FilmDatabase(Context context) {
            super(context, Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/DB/FilmDatabase.db", null, 1);
        }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAIN_TABLE = "CREATE TABLE if not exists " + table_name + "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NomeFilm TEXT, " +
                "URL TEXT," +
                "Description TEXT," +
                "DataOra TEXT )";
        db.execSQL(CREATE_MAIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    //-----------------------------------------
    /*  DELETE FROM .. WHERE id = id inserito SQlite QUERY   */

    /**
     * This method deletes the rows equal to the id value. Id is a primary key so just one row will be deleted
     *
     * @param id to delete fro the table
     * @return the number of rows deleted
     */
    public Integer onDeleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "ID = ?",new String[] {id});
    }


    //-----------------------------------------
    /*INSERT INTO SQlite QUERY*/

    /**
     * This method adds the parameter elements into a row of the table
     *
     * NO ID WILL BE ADDED, BECAUSE IT AUTOINCREMENTS ITSELF!
     * @param NomeFilm second column
     * @param URL third column
     * @param Description fourth column
     * @return false if .insert method from SQLiteOpenHelper inserts the value into the table and return -1, true otherwise.
     */
    public boolean insertData (String NomeFilm, String URL, String Description, String DataOra) {
        boolean b = false;
        try{
            SQLiteDatabase database = this.getWritableDatabase();               // POSSO SCRIVERE SUL DATABASE
        /* RICHIAMO ON CREATE PER ACCERTARMI CHE ESISTA LA TABELLA: se esiste già non fa nulla */
            SQLiteDatabase db =  SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory().toString()+"/SteAPPS/VideoLinks/DB/FilmDatabase.db", null,0);
            ContentValues contentValues = new ContentValues();
        /* Inserisco nelle colonne indicate i parametri ricevuti dal metodo */
            contentValues.put("NomeFilm", NomeFilm);
            contentValues.put("URL",URL);
            contentValues.put("Description",Description);
            contentValues.put("DataOra",DataOra);
            long result = db.insert(table_name,null,contentValues);
            b = result != -1? true:false;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return b;
    }


    //---------------QUERY ANYTHING FROM THE TABLE------------------------

    /**
     * This method is used to get all the data from the table.
     *
     * @return a method contaning the data required. Its containement can't be shown, it needs to be
     *         tranformed into a stirng buffer, so another method which does it is required.
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+table_name,null);
        return res;
    }
}
