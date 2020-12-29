package sa.videolinks.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ament on 29/08/2017.
 */

public class DataEora {

    private int ore = 0;
    private int minuti = 0;
    private int secondi = 0;
    private String ora = null;
    private double giorno = 0;
    private double mese = 0;
    private double anno = 0;
    private String data = null;

    private Date date =new Date();
    GregorianCalendar gcalendar = new GregorianCalendar();


    /**
     * IL COSTRUTTORE DEFINISCE GIA DA SOLO LA DATA E L'ORA CORRENTE
     */
    public DataEora() {
        setOra();
        setData();
    }

    //--------------------------------------------------
    public void setOra () {
        /*
        DA COMPLETARE. PER ORA NON NECESSARIO
         */
        Date t = new Date ();
        ore = gcalendar.get(Calendar.HOUR_OF_DAY);
        minuti = gcalendar.get(Calendar.MINUTE);
        secondi = gcalendar.get(Calendar.SECOND);

        ora = ore + " : " + minuti + " : " + secondi;
    }
    //--------------------------------------------------
    public void setData () {
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        data = sdf.format(date);
        System.out.println("Today is " + sdf.format(date) );
    }
    //--------------------------------------------------
    public void setDataEora () {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); // 2016/11/16 12:08:43
    }
    //--------------------------------------------------

    /**
     * Questo metodo restituisce l'ora. Permette inoltre di aggiungere gli zeri dove il sistema
     * non li mette in automativo.
     *
     * @return orario con le ore provviste di zeri qualora richiesti
     */
    public String getOra () {
        try {
            String arr[] = ora.split(" : ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() != 2) {
                    ArrayList<Character> a = new ArrayList<Character>();
                    a.add('0');
                    a.add(arr[i].charAt(0));
                    String s = arrayListToString(a);
                    arr[i] = s;
                }
            }
            ora = arr[0] + " : " + arr[1] + " : " + arr[2];
            show("ORA CON SPAZI " + ora);
            ora = split(ora, " : ");
            show("ORA senza SPAZI " + ora);
        } catch (Exception exc) {
            System.err.println("Eccezione " + exc.getMessage());
        }
        return ora;
    }
    //--------------------------------------------------
    public String split (String s, String spitter) {
        String arr [] = ora.split(spitter);
        return ora = arr [0] + ":" + arr [1] + ":" + arr[2];
    }
    //--------------------------------------------------
    /**
     * 	Metodo utilizzato per rimuovere un carattere nella posizione specificata
     */
    public String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
    //--------------------------------------------------
    public int countCharacterOccurences (String s,char c) {
        int cont = 0;
        for (int i = 0; i<s.length(); i++) {
            if (s.charAt(i) == c) {
                cont ++;
            }
        }
        return cont;
    }
    //--------------------------------------------------
    public ArrayList <Character> stringToArrayList (String s) {
        ArrayList<Character> a = new ArrayList<Character>();
        try {
            for (int i = 0; i < s.length(); i++) {
                a.add(s.charAt(i));
            }
        } catch (Exception exc) {
            show("ECCEZIONE!");
        }
        return a;
    }
    //--------------------------------------------------
    public String getData () {
        return data;
    }
    //--------------------------------------------------
    public String getDataEora () {
        return data + " " + ora;
    }
    //--------------------------------------------------
    public int deleteCommaFromDouble (Double n) {
        String s = n.toString();
        ArrayList <Character> a = new ArrayList <Character> ();
        if (s.contains(",")) {
            for (int i = 0; i<s.length() && s.charAt(i) != ','; i++) {
                a.add(s.charAt(i));
            }
        } else {
            return -1;
        }
        s = arrayListToString (a);
        int result = Integer.parseInt(s);
        return result;
    }

    //----------------------------------------
    public String arrayListToString (ArrayList <Character> a) {
        StringBuffer s = new StringBuffer();
        try {
            for (int i = 0; i < a.size(); i++) {
                s.append(a.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException exc) {
            show("ECCEZIONE!");
        }
        return s.toString();
    }

    //----------------------------------------
    public void show (Object o) {
        System.err.println(o);
    }
    //----------------------------------------
    public void showArr (Object [] o) {
        for (int i = 0; i< o.length; i++) {
            show(o[i]);
        }
    }
    //----------------------------------------
}
