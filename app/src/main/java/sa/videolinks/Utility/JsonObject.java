package sa.videolinks.Utility;

import java.util.ArrayList;

public class JsonObject {
    public int Id;
    public String NomeFilm;
    public String UrlFilm;

    public JsonObject(int Id, String NomeFilm, String UrlFilm) {
        this.Id=Id;
        this.NomeFilm=NomeFilm;
        this.UrlFilm=UrlFilm;
    }

    public static JsonObject[] getJsonObjectArr(String [] NomeFilm, String [] UrlFilm) {
        ArrayList<JsonObject> jsonObjectArr = new  ArrayList<JsonObject>();
        try {
            for(int i = 0; i< NomeFilm.length; i++) {
                jsonObjectArr.add(
                        new JsonObject(i, NomeFilm[i],UrlFilm[i] )
                );
            }
        } catch (Exception e) {
            return null;
        }
        return jsonObjectArr.toArray(new JsonObject[jsonObjectArr.size()]);
    }

    /**
     * Metodo per l'ottenimento di un oggetto a partire da un array contenente tutti i dati presenti sul database
     * @param allData tutti i dati presenti sul database
     * @return array di JsonObject
     */
    public static JsonObject[] getJsonObjectArr(String [] allData) {
        ArrayList<JsonObject> jsonObjectArr = new  ArrayList<JsonObject>();
        try {
            int index = 0; int index1 = 0; int index2 = 0; int index3 = 0;
            for(int i = 0; i< allData.length - 2; i++) {
                index = i;
                index1 = i == 0 ? 0 : index;
                index2 = i == 0 ? 1 : ++index;
                index3 = i == 0 ? 2 : ++index;
                jsonObjectArr.add(
                        new JsonObject(
                                Integer.parseInt(allData[index1]), // Id
                                allData[index2], // Film
                                allData[index3] ) // Url
                );
                i = index3;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return jsonObjectArr.toArray(new JsonObject[jsonObjectArr.size()]);
    }

}
