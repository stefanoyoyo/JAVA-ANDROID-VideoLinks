package sa.videolinks.Utility;

import java.util.ArrayList;

import sa.videolinks.Interfacce.JsonDataInterface;

/**
 * Created by ament on 03/04/2020.
 */

public class JsonDataConverter implements JsonDataInterface {

    @Override
    public String[] parse(String jsonText, String key) {
        String[] jsonSingleObject = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            char doppiApici = '"';
            // Divido il Json negli oggetti che lo compongono
            //String regex = "\" + key + \": + \"";
            String regex = key;
            String regex2 = "," + String.valueOf(doppiApici);  // ,"
            String regex3 = String.valueOf(doppiApici) + "}"; // "}
            jsonSingleObject = jsonText.split(regex);
            int counter = 0;
            for (int i = 0; i< jsonSingleObject.length; i++) {
                if (i > 0) {
                    jsonSingleObject[i] = jsonSingleObject[i].replaceAll("\":\"","");
                    int indice2 = 0;
                    int indice3 = 0;
                    indice2 = jsonSingleObject[i].indexOf(regex2); --indice2;
                    indice3 = jsonSingleObject[i].indexOf(regex3);
                    if (indice2 < indice3 && i < jsonSingleObject.length -1 && indice2>0) {
                        jsonSingleObject[i] = jsonSingleObject[i].substring(0,indice2);
                        result.add(jsonSingleObject[i]);
                        System.out.println(indice2);
                    } else {
                        if (i == jsonSingleObject.length -1) {
                            int indiceDaUsare = indice2 < indice3 && indice2 >0 ? indice2 : indice3;
                            jsonSingleObject[i] = jsonSingleObject[i].substring(0, indiceDaUsare);
                            result.add(jsonSingleObject[i]);
                            System.out.println(indiceDaUsare);
                        } else {
                            jsonSingleObject[i] = jsonSingleObject[i].substring(0, indice3);
                            result.add(jsonSingleObject[i]);
                            System.out.println(indice3);
                        }
                    }
                }
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return result.toArray(new String[result.size()]);
    }
}

